# Baut eine RELEASE-signierte KindKrank-APK, die sich ueber die installierte
# App (Release-Signatur) aktualisieren laesst. Debug-APKs koennen das NICHT.
#
# Aufruf:
#   powershell -NoProfile -ExecutionPolicy Bypass -File publish-release-apk.ps1
#   ... -VersionCode 1025
#
# Das Passwort wird nur in eine Variable gelesen und nie ausgegeben.

param(
    [int]$VersionCode = 0,
    [int]$TimeoutSeconds = 300
)

$ErrorActionPreference = 'Stop'
$root = 'F:\oc-projects\KindKrank'
$ks = 'F:\prg\keys\kindkrank\kindkrank-release.jks'
$pwFile = 'F:\prg\keys\kindkrank\kindkrank-release-password.txt'
$buildTools = 'C:\Users\Felix D\AppData\Local\Android\Sdk\build-tools\34.0.0'

if (-not (Test-Path -LiteralPath $ks)) { throw "Keystore fehlt: $ks" }
if (-not (Test-Path -LiteralPath $pwFile)) { throw "Passwortdatei fehlt: $pwFile" }

# --- Zugangsdaten einlesen (nur Namen der Schluessel, keine Werte ausgeben) ---
$lines = Get-Content -LiteralPath $pwFile -Encoding UTF8 | Where-Object { $_.Trim() -ne '' }
$map = @{}
foreach ($l in $lines) {
    if ($l -match '^\s*([A-Za-z0-9_.\-]+)\s*[=:]\s*(.+)$') {
        $map[$matches[1].ToLower()] = $matches[2].Trim()
    }
}
$store = $null; $key = $null; $alias = 'release'
foreach ($k in $map.Keys) {
    if ($k -match 'alias') { $alias = $map[$k] }
    elseif ($k -match 'store') { $store = $map[$k] }
    elseif ($k -match 'key') { $key = $map[$k] }
}
if (-not $store) { $store = $lines[0].Trim() }
if (-not $key) { $key = $store }

# --- versionCode bestimmen: 1000 + (letzte CI-Buildnummer + 1) ---
function Get-NextVersionCode {
    try {
        $tag = & gh release list -R felix-dieterle/KindKrank -L 1 --json tagName --jq '.[0].tagName' 2>$null
        if ($tag -match '-(\d+)$') { return 1000 + [int]$matches[1] + 1 }
    } catch { }
    try {
        $count = [int](& git -C $root rev-list --count HEAD 2>$null)
        return 1000 + $count + 1
    } catch {
        return 1001
    }
}
if ($VersionCode -le 0) { $VersionCode = Get-NextVersionCode }
if ($VersionCode -lt 1000) { $VersionCode = 1000 + $VersionCode }

Write-Output "versionCode=$VersionCode"

# --- Build ---
$log = Join-Path $root 'publish-build.log'
$gradleArgs = @(
    ':app:assembleRelease',
    '--no-daemon',
    '--console=plain',
    "-PversionCode=$VersionCode",
    "-Pandroid.injected.signing.store.file=$ks",
    "-Pandroid.injected.signing.store.password=$store",
    "-Pandroid.injected.signing.key.alias=$alias",
    "-Pandroid.injected.signing.key.password=$key"
)
$p = Start-Process -FilePath (Join-Path $root 'gradlew.bat') -ArgumentList $gradleArgs `
    -WorkingDirectory $root -PassThru -WindowStyle Hidden `
    -RedirectStandardOutput $log -RedirectStandardError "$log.err"
if (-not $p.WaitForExit($TimeoutSeconds * 1000)) {
    & taskkill /PID $p.Id /T /F | Out-Null
    throw "BUILD TIMEOUT nach $TimeoutSeconds s (siehe $log)"
}
$p.Refresh()
$exitOk = $false
try { $exitOk = ($p.ExitCode -eq 0) } catch { $exitOk = $false }
if (-not $exitOk) {
    $exitOk = (Select-String -LiteralPath $log -Pattern 'BUILD SUCCESSFUL' -SimpleMatch -Quiet)
}
if (-not $exitOk) {
    Get-Content -LiteralPath $log -Encoding UTF8 | Select-Object -Last 25
    throw "BUILD FEHLGESCHLAGEN (ExitCode $($p.ExitCode), siehe $log)"
}

$apk = Join-Path $root 'app\build\outputs\apk\release\app-release.apk'
if (-not (Test-Path -LiteralPath $apk)) { throw "APK nicht gefunden: $apk" }

# --- Verifikation ---
$badging = & (Join-Path $buildTools 'aapt2.exe') dump badging $apk 2>$null |
    Select-String 'package:' | Select-Object -First 1
$cert = & (Join-Path $buildTools 'apksigner.bat') verify --print-certs $apk 2>$null |
    Select-String 'SHA-256 digest' | Select-Object -First 1
$hash = (Get-FileHash -LiteralPath $apk -Algorithm SHA256).Hash
$size = (Get-Item -LiteralPath $apk).Length

Write-Output $badging.Line
Write-Output $cert.Line
Write-Output ("SHA256={0}" -f $hash)
Write-Output ("SIZE={0}" -f $size)
Write-Output ("APK={0}" -f $apk)

$digest = ($cert.Line -replace '.*digest:\s*', '').Trim().Replace(':', '').ToLower()
$expected = 'eb7dea090f68ab2371542c13e9ca46e1746285bf5a4d08b2bb347413cd768f44'
if ($digest -and $digest -ne $expected) {
    Write-Warning "Zertifikat $digest weicht vom Release-Zertifikat $expected ab - Update ueber die installierte App schlaegt fehl!"
}

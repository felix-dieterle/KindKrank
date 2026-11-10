# KindKrank (Android-App)

Kotlin/Compose-App zum Thema "Kind krank" (DE/CH-Recht, Betreuungsurlaub,
Grenzgaenger). Paket: `com.kindkrank.app`. Kein Backend, keine Persistenz.

## Build & Verifikation

- Debug-APK: `app\build\outputs\apk\debug\app-debug.apk`
- Release laeuft ueber den GitHub-Workflow (fester Keystore in
  `F:\prg\keys\kindkrank\` - **niemals lesen oder ins Repo legen**).
- Standard-Build: `.\gradlew.bat :app:assembleDebug --no-daemon --console=plain`

### Pflicht: kein Gradle-Daemon in Tool-Calls

Am 09.11.2026 blieb ein Tool-Call auf
`.\gradlew.bat :app:compileDebugKotlin` haengen. Der Gradle-Daemon (PID 26884)
lief danach noch **3 Stunden** (Idle-Timeout 180 min) weiter und hielt den
geerbten Listen-Socket von Port 4097. Folge: Der Chat-Timeout nach 30 Minuten,
danach drei fehlgeschlagene Server-Restarts (22:05/23:05/00:05) und rund 3
Stunden kein OpenCode-Server.

Deshalb gilt hier:

1. Gradle **immer** mit `--no-daemon --console=plain` aufrufen.
2. Nie unbegrenzt auf einen Build warten. Statt direkt `gradlew` im Tool-Call:

```powershell
$log = "F:\oc-projects\KindKrank\build-check.log"
$p = Start-Process -FilePath ".\gradlew.bat" `
    -ArgumentList ":app:assembleDebug","--no-daemon","--console=plain" `
    -WorkingDirectory "F:\oc-projects\KindKrank" -PassThru -WindowStyle Hidden `
    -RedirectStandardOutput $log -RedirectStandardError "$log.err"
if (-not $p.WaitForExit(240000)) {
    taskkill /PID $p.Id /T /F | Out-Null
    "BUILD TIMEOUT nach 4 Minuten"
} else {
    $p.ExitCode
}
Get-Content $log -Encoding UTF8 | Select-Object -Last 20
```

3. Nach jedem Build das Ergebnis pruefen (ExitCode 0 + Logzeile
   `BUILD SUCCESSFUL`), nicht nur "Datei existiert".

## Stand 10.11.2026

- Uncommitted: Umbau "Copy -> Share" plus Gesetzeslinks
  (`ShareableInfoCard.kt`, `LegalLinksRow.kt`, `LawLinks.kt`,
  `ShareUtils.kt` neu; `CopyableInfoCard.kt` entfernt) sowie die
  5-Jahres-Regel im Betreuungs-Wizard.
- Dieser Stand wurde **nie kompiliert** (Build hing am 09.11. um 21:18).
  Vor dem naechsten APK-Versand: bauen, pruefen, committen.

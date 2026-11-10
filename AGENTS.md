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
$p = Start-Process -FilePath "F:\oc-projects\KindKrank\gradlew.bat" `
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
   `Start-Process -FilePath` braucht den **absoluten** Pfad
   (`-WorkingDirectory` loest den Dateinamen nicht auf).

## Update-Faehigkeit (Pflicht vor jedem APK-Versand)

Ein APK ersetzt eine installierte App nur, wenn **alle** Punkte stimmen:

1. **Gleicher `applicationId`** (`com.kindkrank.app`) - kein Wechsel.
2. **Gleiche Signatur.** Chat-APKs sind Debug-Builds, signiert mit
   `%USERPROFILE%\.android\debug.keystore` dieses PCs -> nur von hier bauen
   und versenden. Die GitHub-Release-APKs sind mit dem Release-Keystore
   (`secrets.KEYSTORE_BASE64`) signiert und damit eine **eigene Spur**:
   Wechsel Debug <-> Release erfordert Deinstallation (Datenverlust).
3. **`versionCode` streng groesser** als installiert (`versionName` ist egal).
   Automatisch: `versionCode = 1000 + git commit count` (Default in
   `app/build.gradle.kts`, ueberschreibbar mit `-PversionCode=N`; Werte < 1000
   werden auf `1000 + N` gehoben, damit der Release-Workflow mit
   `run_number` nicht unter die Debug-Versionen rutscht). Gleicher Code-Stand
   ohne neuen Commit: **`-PversionCode=<alt+1>`** setzen.
4. Kein `minSdk`/`targetSdk`-Problem: Downgrade nur mit `adb install -d`.

Verifikation vor dem Versand:

```powershell
& "C:\Users\Felix D\AppData\Local\Android\Sdk\build-tools\34.0.0\aapt2.exe" `
  dump badging "F:\oc-projects\KindKrank\app\build\outputs\apk\debug\app-debug.apk" |
  Select-String "package:"
```

## Stand 10.11.2026

- Share statt Copy + Gesetzeslinks, 5-Jahres-Regel, versionCode-Automatik:
  committet und gepusht (`cf45f8d`).
- Chat-APK v1.1 (versionCode 1051) gebaut und per Telegram versendet -
  installierbar ueber v1.0 (versionCode 1).

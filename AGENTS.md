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
2. **Gleiche Signatur.** Auf dem Handy ist die **Release-Signatur**
   (Zertifikat `EB:7D:EA:09...`, Keystore `F:\prg\keys\kindkrank\`).
   Alle bisherigen Chat-APKs ab Build 15 waren Release-Builds. **Debug-APKs
   (Zertifikat `59:9F:98:4F...`) koennen die installierte App NICHT
   aktualisieren** - Android meldet dann nur "App wurde nicht installiert".
   Deshalb fuer den Chat **immer** `publish-release-apk.ps1` benutzen
   (baut + signiert release, prueft Zertifikat/SHA, kein Debug-Build).
   Deinstallation (Datenverlust) nur als letzter Ausweg.
3. **`versionCode` streng groesser** als installiert (`versionName` ist egal).
   `app/build.gradle.kts` nutzt jetzt `resolveVersionCode()`: explizite Werte
   < 1000 werden auf `1000 + N` gehoben. CI: `1000 + run_number`
   (z. B. 1018). Lokal: `publish-release-apk.ps1` nimmt automatisch
   `1000 + letzte CI-Buildnummer + 1`.
4. Kein `minSdk`/`targetSdk`-Problem: Downgrade nur mit `adb install -d`.

Verifikation vor dem Versand (macht das Skript selbst):

```powershell
& "C:\Users\Felix D\AppData\Local\Android\Sdk\build-tools\34.0.0\aapt2.exe" `
  dump badging "F:\oc-projects\KindKrank\app\build\outputs\apk\release\app-release.apk" |
  Select-String "package:"
& "C:\Users\Felix D\AppData\Local\Android\Sdk\build-tools\34.0.0\apksigner.bat" `
  verify --print-certs "F:\oc-projects\KindKrank\app\build\outputs\apk\release\app-release.apk"
```

### `adb` nie ohne Timeout

Am 10.11.2026 hing ein Tool-Call `adb devices; adb shell dumpsys package ...`
(kein Telefon verbunden) > 29 Minuten und riss den Chat in den 30-Minuten-
Timeout. `adb` immer nur nach `adb devices`-Pruefung und mit hartem Timeout
(`Start-Process` + `WaitForExit(30000)` + `taskkill /T /F`) aufrufen.

## Stand 10.11.2026

- Share statt Copy + Gesetzeslinks, 5-Jahres-Regel, versionCode-Automatik:
  committet und gepusht (`cf45f8d`, `97b2bd3`).
- **Erkenntnis:** Die installierte App ist release-signiert; die Debug-APKs
  (versionCode 1051/1052) waren deshalb nicht installierbar.
- Release-APK **v1.1 (Build 17, versionCode 17)** per Telegram versendet
  (message_id 8470) - updatefaehig ueber Build 14/15/16.
- Neues Skript `publish-release-apk.ps1` (release-signiert, versionCode
  automatisch, Verifikation).

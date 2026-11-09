# KindKrank

Android App die sich darauf fokussiert per ganz einfachen und intuitiven wizard die Eltern die Schritte zu leiten was zu tun ist wenn das Kind krank ist.

## Tech Stack

- **Kotlin** – offizielle Sprache für Android-Entwicklung
- **Jetpack Compose** – modernes, deklaratives UI-Framework von Google
- **Material Design 3** – natives Android Design-System
- **Navigation Compose** – typsicheres In-App-Routing
- **Android SDK** – Minimum API 24 (Android 7.0), Target API 35

## Projekt-Struktur

```
app/src/main/
├── kotlin/com/kindkrank/app/
│   ├── MainActivity.kt              # App-Einstiegspunkt
│   ├── data/
│   │   ├── KrankenkasseData.kt      # Datensatz der 5 größten Krankenkassen
│   │   └── CareRules.kt             # Regeln DE + CH-Grenzgänger, Anspruchs-Engine
│   ├── navigation/
│   │   ├── Screen.kt                # Navigations-Routen
│   │   └── AppNavigation.kt         # NavHost-Konfiguration
│   └── ui/
│       ├── theme/                   # Farben, Typografie, Theme
│       ├── components/
│       │   ├── CountrySelector.kt    # DE/CH-Auswahl + CountryBadge
│       │   ├── CopyableInfoCard.kt
│       │   └── WizardProgressIndicator.kt
│       └── screens/
│           ├── DisclaimerScreen.kt  # Rechtlicher Hinweis (App-Start)
│           ├── HomeScreen.kt        # Zwei Wege: Kind krank / Betreuungsperson krank
│           ├── CaregiverWizardScreen.kt # 4-Schritte-Zweig Betreuungsperson krank
│           ├── GrenzgaengerSchweizScreen.kt
│           ├── SpecialCasesScreen.kt
│           └── wizard/
│               ├── Step1FamilySituation.kt
│               ├── Step2Krankenkasse.kt
│               ├── Step3Doctor.kt
│               └── Step4EmployerAndInsurance.kt
└── res/
    ├── values/  (strings, colors, themes)
    └── xml/     (backup rules)
```

## Wizard-Flow

1. **Rechtlicher Hinweis** – beim Start angezeigt, Haftungsausschluss gem. § 675 BGB
2. **Startseite** – Auswahl „Wo arbeiten Sie?“ (Deutschland / Schweiz-Grenzgänger)
   und zwei gleichwertige Wege:
   - **Mein Kind ist krank** (bestehender Kinderkrankengeld-Flow)
   - **Betreuungsperson ist krank** (Mutter/Partner/in fällt aus, Kind gesund)
3. **Kind krank, DE** – Familiensituation → Krankenkasse → Arzt (Muster 21) → Arbeitgeber & Geld
4. **Kind krank, CH** – Grenzgänger-Übersicht (Art. 36 ArG / Art. 329h OR)
5. **Betreuungsperson krank** – 4 interne Schritte:
   1. Situation & Anspruch (sofort heute)
   2. Nachweise
   3. Arbeitgeber informieren (mit kopierbarer Textvorlage)
   4. Geld & rechtliche Grundlage
6. **Sonderfälle** – Krankenhaus, Behinderung, mehrere Kinder, Beamte u.a.

### Länder-Logik

| | Arbeit in Deutschland | Grenzgänger in die Schweiz |
|---|---|---|
| Sofort | § 275 Abs. 3 BGB | Art. 36 Abs. 3 ArG |
| Bezahlt | § 616 BGB (ca. 5 Tage/Jahr) | Art. 329h OR (3 Tage/Ereignis, max. 10/Dienstjahr) |
| Länger | § 2 PflegeZG + Pflegeunterstützungsgeld | Art. 324a OR, GAV/Krankentaggeld |
| Kind krank | § 45 SGB V, Muster 21 | Art. 36 ArG / Art. 329h OR, kein deutsches Kinderkrankengeld |

Die Regeln liegen testbar in `data/CareRules.kt` (`GermanCareRules`, `SwissCrossBorderRules`).

## Build-Voraussetzungen

- Android Studio Ladybug (2024.2) oder neuer
- Android SDK API 35
- JDK 17+

## Build-Schritte

```bash
# 1. Repository klonen
git clone https://github.com/felix-dieterle/KindKrank.git
cd KindKrank

# 2. In Android Studio öffnen
#    File → Open → KindKrank-Verzeichnis auswählen

# 3. Oder über die Kommandozeile (mit Gradle Wrapper)
./gradlew assembleDebug

# 4. APK befindet sich unter:
#    app/build/outputs/apk/debug/app-debug.apk
```

## Unterstützte Krankenkassen

| Kasse | Hotline | Online-Portal |
|-------|---------|---------------|
| TK (Techniker Krankenkasse) | 0800 285 85 85 | tk.de |
| AOK | 0800 0 10 20 20 | aok.de |
| Barmer | 0800 333 1010 | barmer.de |
| DAK Gesundheit | 040 325 325 325 | dak.de |
| KKH | 0800 2 55 82 55 | kkh.de |

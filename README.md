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
│   │   └── KrankenkasseData.kt      # Datensatz der 5 größten Krankenkassen
│   ├── navigation/
│   │   ├── Screen.kt                # Navigations-Routen
│   │   └── AppNavigation.kt         # NavHost-Konfiguration
│   └── ui/
│       ├── theme/                   # Farben, Typografie, Theme
│       ├── components/
│       │   └── WizardProgressIndicator.kt
│       └── screens/
│           ├── DisclaimerScreen.kt  # Rechtlicher Hinweis (App-Start)
│           ├── HomeScreen.kt        # "Mein Kind ist krank"-Button
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
2. **Startseite** – `Mein Kind ist krank` Button
3. **Schritt 1** – Familiensituation (beide Eltern / Alleinerziehend)
4. **Schritt 2** – Krankenkasse wählen (TK, AOK, Barmer, DAK, KKH)
5. **Schritt 3** – Arztbesuch & Kinderkrankenschein (Muster 21)
6. **Schritt 4** – Arbeitgeber informieren & Kinderkrankengeld beantragen
   - Kassenspezifische Links zum Online-Portal
   - Direktwahl der Hotline
7. **Sonderfälle** – Krankenhaus, Behinderung, mehrere Kinder, Beamte u.a.

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

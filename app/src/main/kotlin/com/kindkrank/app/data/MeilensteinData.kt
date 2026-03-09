package com.kindkrank.app.data

data class Meilenstein(
    val id: String,
    val altersgruppe: String,
    val beschreibung: String,
)

val MEILENSTEINE: List<Meilenstein> = listOf(
    // 0–3 Monate
    Meilenstein("m01", "0–3 Monate", "Verfolgt Gesichter und Gegenstände mit den Augen"),
    Meilenstein("m02", "0–3 Monate", "Hebt Kopf kurz in Bauchlage"),
    Meilenstein("m03", "0–3 Monate", "Erstes soziales Lächeln"),

    // 3–6 Monate
    Meilenstein("m04", "3–6 Monate", "Dreht sich von Bauch auf Rücken"),
    Meilenstein("m05", "3–6 Monate", "Greift gezielt nach Gegenständen"),
    Meilenstein("m06", "3–6 Monate", "Lacht laut"),

    // 6–9 Monate
    Meilenstein("m07", "6–9 Monate", "Sitzt mit Unterstützung"),
    Meilenstein("m08", "6–9 Monate", "Babelt (z. B. „ba-ba", „ma-ma")"),
    Meilenstein("m09", "6–9 Monate", "Übergibt Gegenstand von einer Hand zur anderen"),

    // 9–12 Monate
    Meilenstein("m10", "9–12 Monate", "Steht an Möbeln"),
    Meilenstein("m11", "9–12 Monate", "Klatscht in die Hände"),
    Meilenstein("m12", "9–12 Monate", "Versteht einfache Aufforderungen (z. B. „Nein")"),

    // 12–18 Monate
    Meilenstein("m13", "12–18 Monate", "Erste selbstständige Schritte"),
    Meilenstein("m14", "12–18 Monate", "Sagt erste Wörter (außer „Mama"/„Papa")"),
    Meilenstein("m15", "12–18 Monate", "Zeigt auf gewünschte Gegenstände"),

    // 18–24 Monate
    Meilenstein("m16", "18–24 Monate", "Läuft sicher und fällt selten"),
    Meilenstein("m17", "18–24 Monate", "Zeigt auf Bilder in Büchern"),
    Meilenstein("m18", "18–24 Monate", "Wortschatz von mindestens 10 Wörtern"),

    // 2–3 Jahre
    Meilenstein("m19", "2–3 Jahre", "Bildet Zwei- bis Dreiwortsätze"),
    Meilenstein("m20", "2–3 Jahre", "Malt Kreise und einfache Formen"),
    Meilenstein("m21", "2–3 Jahre", "Spielt Rollenspiele (z. B. Kochen, Telefonieren)"),

    // 3–4 Jahre
    Meilenstein("m22", "3–4 Jahre", "Steht kurz auf einem Bein"),
    Meilenstein("m23", "3–4 Jahre", "Kennt und benennt Grundfarben"),
    Meilenstein("m24", "3–4 Jahre", "Fragt häufig „Warum?""),
)

package com.kindkrank.app.data

data class Krankenkasse(
    val id: String,
    val name: String,
    val shortName: String,
    val onlinePortalUrl: String,
    val hotline: String,
    val digitalSubmissionNote: String,
)

val KRANKENKASSEN = listOf(
    Krankenkasse(
        id = "tk",
        name = "Techniker Krankenkasse",
        shortName = "TK",
        onlinePortalUrl = "https://www.tk.de/service/app/2055448/kinderkrankengeld.app",
        hotline = "0800 285 85 85",
        digitalSubmissionNote = "Die TK ermöglicht die digitale Einreichung über die TK-App oder das Online-Portal.",
    ),
    Krankenkasse(
        id = "aok",
        name = "AOK",
        shortName = "AOK",
        onlinePortalUrl = "https://www.aok.de/pk/leistungen/kinder/kind-krank/",
        hotline = "0800 0 10 20 20",
        digitalSubmissionNote = "Nutzen Sie das AOK Online-Portal oder die AOK App zur Einreichung.",
    ),
    Krankenkasse(
        id = "barmer",
        name = "Barmer",
        shortName = "Barmer",
        onlinePortalUrl = "https://www.barmer.de/unsere-leistungen/leistungen-a-z/kinderkrankengeld",
        hotline = "0800 333 1010",
        digitalSubmissionNote = "Barmer bietet die Einreichung per App oder Online-Service an.",
    ),
    Krankenkasse(
        id = "dak",
        name = "DAK Gesundheit",
        shortName = "DAK",
        onlinePortalUrl = "https://www.dak.de/dak/leistungen/kinderkrankengeld/",
        hotline = "040 325 325 325",
        digitalSubmissionNote = "DAK Gesundheit bietet einen Online-Antrag für Kinderkrankengeld an.",
    ),
    Krankenkasse(
        id = "kkh",
        name = "KKH Kaufmännische Krankenkasse",
        shortName = "KKH",
        onlinePortalUrl = "https://www.kkh.de/leistungen/kinderkrankengeld",
        hotline = "0800 2 55 82 55",
        digitalSubmissionNote = "Die KKH ermöglicht die Antragstellung über das Online-Portal.",
    ),
)

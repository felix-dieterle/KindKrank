package com.kindkrank.app.data

data class LegalLink(val label: String, val url: String)

/**
 * Direktlinks auf den Wortlaut der genannten Normen.
 *
 * Deutschland: gesetze-im-internet.de (amtliche Online-Fassung).
 * Schweiz: fedlex.admin.ch (amtliche Sammlung, Artikelsprung via #art_...).
 */
object LawLinks {
    val BGB_275 = LegalLink(
        "\u00A7 275 BGB",
        "https://www.gesetze-im-internet.de/bgb/__275.html",
    )
    val BGB_616 = LegalLink(
        "\u00A7 616 BGB",
        "https://www.gesetze-im-internet.de/bgb/__616.html",
    )
    val BGB_675 = LegalLink(
        "\u00A7 675 BGB",
        "https://www.gesetze-im-internet.de/bgb/__675.html",
    )
    val SGB_V_10 = LegalLink(
        "\u00A7 10 SGB V",
        "https://www.gesetze-im-internet.de/sgb_5/__10.html",
    )
    val SGB_V_45 = LegalLink(
        "\u00A7 45 SGB V",
        "https://www.gesetze-im-internet.de/sgb_5/__45.html",
    )
    val SGB_XI_44A = LegalLink(
        "\u00A7 44a SGB XI",
        "https://www.gesetze-im-internet.de/sgb_11/__44a.html",
    )
    val PFLEGEZG_2 = LegalLink(
        "\u00A7 2 PflegeZG",
        "https://www.gesetze-im-internet.de/pflegezg/__2.html",
    )
    val PFLEGEZG_7 = LegalLink(
        "\u00A7 7 PflegeZG",
        "https://www.gesetze-im-internet.de/pflegezg/__7.html",
    )
    val ARG_36 = LegalLink(
        "Art. 36 ArG",
        "https://www.fedlex.admin.ch/eli/cc/1966/57_57_57/de#art_36",
    )
    val OR_329H = LegalLink(
        "Art. 329h OR",
        "https://www.fedlex.admin.ch/eli/cc/27/317_321_377/de#art_329_h",
    )
    val OR_324A = LegalLink(
        "Art. 324a OR",
        "https://www.fedlex.admin.ch/eli/cc/27/317_321_377/de#art_324_a",
    )
    val ZGB_272_276 = LegalLink(
        "Art. 272/276 ZGB",
        "https://www.fedlex.admin.ch/eli/cc/24/233_245_233/de#art_272",
    )
}

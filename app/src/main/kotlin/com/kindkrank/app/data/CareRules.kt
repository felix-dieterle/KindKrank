package com.kindkrank.app.data

/**
 * Wo arbeitet der Nutzer? Der Wohnort ist in beiden Fällen Deutschland.
 */
enum class WorkCountry(
    val code: String,
    val label: String,
    val flag: String,
    val subtitle: String,
) {
    GERMANY(
        code = "DE",
        label = "Deutschland",
        flag = "\uD83C\uDDE9\uD83C\uDDEA",
        subtitle = "Ich arbeite in Deutschland",
    ),
    SWITZERLAND(
        code = "CH",
        label = "Schweiz",
        flag = "\uD83C\uDDE8\uD83C\uDDED",
        subtitle = "Grenzg\u00E4nger: wohne in DE, arbeite in CH",
    );

    companion object {
        fun fromCode(code: String?): WorkCountry =
            values().firstOrNull { it.code == code } ?: GERMANY
    }
}

data class LegalBasis(val label: String, val text: String)

data class CareStep(val title: String, val text: String)

data class MoneySource(val label: String, val text: String)

/**
 * Fertig aufbereiteter Anspruch für die Anzeige im Wizard.
 */
data class Entitlement(
    val title: String,
    val subtitle: String,
    val dayLabel: String,
    val dayValue: String,
    val paySource: String,
    val immediateAction: String,
    val legalBasis: List<LegalBasis>,
    val steps: List<CareStep>,
    val documents: List<String>,
    val moneySources: List<MoneySource>,
    val warnings: List<String>,
    val employerTemplate: String,
)

interface CareRules {
    val country: WorkCountry
    fun caregiverSick(): Entitlement
}

/**
 * Arbeit in Deutschland, Kind gesund, Betreuungsperson (z. B. Mutter) krank.
 *
 * Wichtig: Das ist KEIN Kinderkrankengeld-Fall. Eine AU für den gesunden Vater gibt es nicht.
 */
object GermanCareRules : CareRules {
    override val country = WorkCountry.GERMANY

    override fun caregiverSick() = Entitlement(
        title = "Betreuungsperson krank",
        subtitle = "Sie arbeiten in Deutschland \u2013 Ihr Kind ist gesund, aber die Mutter / " +
            "Betreuungsperson kann heute nicht betreuen.",
        dayLabel = "Kurzzeitige Arbeitsverhinderung",
        dayValue = "bis 10 Arbeitstage",
        paySource = "Pflegeunterst\u00FCtzungsgeld der Pflegekasse der Mutter (90\u00A0% netto, " +
            "2026 max. 135,63\u00A0\u20AC/Tag)",
        immediateAction = "Informieren Sie heute sofort Ihren Arbeitgeber, dass Sie wegen der " +
            "Erkrankung der Mutter die Kinderbetreuung \u00FCbernehmen m\u00FCssen. Eine " +
            "Krankschreibung (AU) f\u00FCr Sie als gesunden Vater ist nicht m\u00F6glich \u2013 " +
            "die \u00E4rztliche Bescheinigung stellt der Arzt f\u00FCr die Mutter aus.",
        legalBasis = listOf(
            LegalBasis(
                "\u00A7 275 Abs. 3 BGB",
                "Die Arbeitsleistung ist unzumutbar, wenn Sie Ihr Kind betreuen m\u00FCssen und " +
                    "keine andere Betreuung verf\u00FCgbar ist. Sie d\u00FCrfen fernbleiben \u2013 " +
                    "ohne Pflichtverletzung, aber zun\u00E4chst unbezahlt.",
            ),
            LegalBasis(
                "\u00A7 616 BGB",
                "Unvorhergesehene Erkrankung eines nahen Angeh\u00F6rigen: ca. 5 Arbeitstage pro " +
                    "Jahr gelten als verh\u00E4ltnism\u00E4\u00DFig nicht erhebliche Zeit. Achtung: " +
                    "in vielen Arbeits- und Tarifvertr\u00E4gen ausgeschlossen.",
            ),
            LegalBasis(
                "\u00A7 2 PflegeZG / \u00A7 44a SGB XI",
                "Kurzzeitige Arbeitsverhinderung bei akuter Pflegesituation: 10 Arbeitstage pro " +
                    "Kalenderjahr plus Pflegeunterst\u00FCtzungsgeld der Pflegekasse der Mutter. " +
                    "Ein Pflegegrad ist nicht n\u00F6tig \u2013 \u201Evoraussichtlich pflegebed\u00FCrftig\u201C gen\u00FCgt.",
            ),
            LegalBasis(
                "\u00A7 45 SGB V",
                "Wird das Kind zus\u00E4tzlich krank, greift Kinderkrankengeld \u2013 dann ist " +
                    "auch \u201Ekeine andere Person im Haushalt\u201C erf\u00FCllt, weil die Mutter ausf\u00E4llt.",
            ),
        ),
        steps = listOf(
            CareStep(
                "Heute: Arbeitgeber informieren",
                "Rufen Sie sofort an oder schreiben Sie eine E-Mail. Nennen Sie den Grund: " +
                    "Partnerin erkrankt, Kinderbetreuung muss \u00FCbernommen werden, keine " +
                    "andere Betreuung verf\u00FCgbar.",
            ),
            CareStep(
                "\u00C4rztliche Bescheinigung der Mutter besorgen",
                "Lassen Sie sich von der Mutter eine Bescheinigung \u00FCber die Erkrankung bzw. " +
                    "die Unf\u00E4higkeit zur Kinderbetreuung ausstellen. Eine AU des Vaters ist " +
                    "weder n\u00F6tig noch m\u00F6glich.",
            ),
            CareStep(
                "Kurzzeitige Arbeitsverhinderung anmelden",
                "Melden Sie bei Ihrem Arbeitgeber die Freistellung nach \u00A7 2 PflegeZG an: " +
                    "bis zu 10 Arbeitstage pro Kalenderjahr. Die Anmeldung soll unverz\u00FCglich erfolgen.",
            ),
            CareStep(
                "Pflegeunterst\u00FCtzungsgeld beantragen",
                "Der Antrag geht an die Pflegekasse der Mutter (nicht an Ihre eigene Kasse). " +
                    "Zahlt 90\u00A0% des Nettolohns, 2026 max. 135,63\u00A0\u20AC pro Tag.",
            ),
            CareStep(
                "Restliche Zeit \u00FCberbr\u00FCcken",
                "Danach: Urlaub, Zeitkonto, Homeoffice (kein gesetzlicher Anspruch) oder " +
                    "unbezahlte Freistellung per Vereinbarung. Ersatzbetreuung organisieren.",
            ),
        ),
        documents = listOf(
            "\u00C4rztliche Bescheinigung \u00FCber die Erkrankung der Mutter",
            "Ggf. Pflegegrad-Bescheid (nicht zwingend)",
            "Mitteilung / Formular des Arbeitgebers zur Freistellung",
            "Antrag auf Pflegeunterst\u00FCtzungsgeld bei der Pflegekasse der Mutter",
            "Bei Zusatzfall: Kinderkrankenschein (Muster\u00A021) f\u00FCr das kranke Kind",
        ),
        moneySources = listOf(
            MoneySource(
                "Pflegekasse der Mutter",
                "Pflegeunterst\u00FCtzungsgeld \u2013 90\u00A0% netto, 2026 max. 135,63\u00A0\u20AC/Tag",
            ),
            MoneySource(
                "Arbeitgeber",
                "Lohnfortzahlung nach \u00A7 616 BGB, sofern nicht vertraglich ausgeschlossen",
            ),
            MoneySource(
                "Krankenkasse (nur bei krankem Kind)",
                "Kinderkrankengeld nach \u00A7 45 SGB V \u2013 nicht bei gesundem Kind",
            ),
        ),
        warnings = listOf(
            "Eine Krankschreibung (AU) f\u00FCr Sie als gesunden Vater ist nicht m\u00F6glich. " +
                "Ein Arzt darf Sie nicht krankschreiben, nur weil die Mutter krank ist.",
            "\u00A7 616 BGB ist in vielen Arbeits- und Tarifvertr\u00E4gen ausgeschlossen \u2013 " +
                "pr\u00FCfen Sie Ihren Vertrag.",
            "Wird das Kind zus\u00E4tzlich krank, wechseln Sie in den Kinderkrankengeld-Fall " +
                "(\u00A7 45 SGB V, Muster\u00A021).",
        ),
        employerTemplate = "Hallo [Name],\n\n" +
            "ich kann heute nicht zur Arbeit kommen, weil meine Partnerin erkrankt ist und ich " +
            "unsere Kinder betreuen muss. Eine andere Betreuung ist kurzfristig nicht verf\u00FCgbar.\n\n" +
            "Ich mache die kurzzeitige Arbeitsverhinderung nach \u00A7 2 PflegeZG geltend. Eine " +
            "\u00E4rztliche Bescheinigung meiner Partnerin reiche ich nach. Bitte best\u00E4tigen Sie " +
            "mir den Eingang.\n\nViele Gr\u00FC\u00DFe\n[Ihr Name]",
    )
}

/**
 * Grenzgänger: Wohnort Deutschland, Arbeitgeber in der Schweiz.
 *
 * Arbeitsrecht und Lohnfortzahlung richten sich nach Schweizer Recht. Die deutsche
 * Familienversicherung (z. B. TK) deckt medizinische Kosten, nicht den Lohn des Vaters.
 */
object SwissCrossBorderRules : CareRules {
    override val country = WorkCountry.SWITZERLAND

    override fun caregiverSick() = Entitlement(
        title = "Betreuungsperson krank \u2013 Grenzg\u00E4nger",
        subtitle = "Sie wohnen in Deutschland, arbeiten aber in der Schweiz. Ihr Kind ist gesund, " +
            "die Mutter / Betreuungsperson kann heute nicht betreuen.",
        dayLabel = "Bezahlte Freistellung",
        dayValue = "3 Arbeitstage pro Ereignis",
        paySource = "Schweizer Arbeitgeber (100\u00A0% Lohn, max. 10 Tage/Dienstjahr); " +
            "danach Lohnfortzahlung nach Art.\u00A0324a OR",
        immediateAction = "Informieren Sie heute sofort Ihren Schweizer Arbeitgeber. Sie haben " +
            "Anspruch auf Freistellung (Art.\u00A036 Abs.\u00A03 ArG) und bezahlte Freistellung f\u00FCr " +
            "die Betreuung (Art.\u00A0329h OR). Eine deutsche AU ist nicht n\u00F6tig \u2013 ein " +
            "Arztzeugnis der Mutter gen\u00FCgt.",
        legalBasis = listOf(
            LegalBasis(
                "Art. 36 Abs. 3 ArG",
                "Freistellung von der Arbeit, wenn Sie pers\u00F6nlich die Betreuung eines kranken " +
                    "Kindes \u00FCbernehmen m\u00FCssen \u2013 bis zu 3 Arbeitstage pro Ereignis. " +
                    "F\u00FCr Kinder gilt keine Jahresgrenze (Abs. 4).",
            ),
            LegalBasis(
                "Art. 329h OR",
                "Bezahlte Freistellung f\u00FCr die Betreuung eines kranken Kindes: 100\u00A0% Lohn, " +
                    "3 Tage pro Ereignis, maximal 10 Tage pro Dienstjahr.",
            ),
            LegalBasis(
                "Art. 324a OR",
                "Bei l\u00E4ngerer Betreuung: Lohnfortzahlung nach Dienstjahr-Skala, weil die " +
                    "Betreuung eine gesetzliche Pflicht ist (Art.\u00A0272/276 ZGB) \u2013 bis eine " +
                    "Ersatzbetreuung organisiert ist.",
            ),
            LegalBasis(
                "GAV / Krankentaggeld",
                "Viele Gesamtarbeitsvertr\u00E4ge oder Arbeitgeber bieten mehr als die gesetzlichen " +
                    "3 Tage. Pr\u00FCfen Sie Arbeitsvertrag und GAV.",
            ),
        ),
        steps = listOf(
            CareStep(
                "Heute: Schweizer Arbeitgeber informieren",
                "Rufen Sie sofort an oder schreiben Sie eine E-Mail. Nennen Sie den Grund und " +
                    "berufen Sie sich auf Art.\u00A036 Abs.\u00A03 ArG.",
            ),
            CareStep(
                "\u00C4rztliches Zeugnis der Mutter besorgen",
                "Ein Arztzeugnis aus Deutschland oder der Schweiz ist g\u00FCltig. Es belegt, dass " +
                    "die Mutter nicht betreuen kann.",
            ),
            CareStep(
                "Bezahlte Freistellung geltend machen",
                "Art.\u00A0329h OR: 100\u00A0% Lohn, 3 Tage pro Ereignis, max. 10 Tage pro Dienstjahr. " +
                    "Die Freistellung nach Art.\u00A036 ArG gilt zus\u00E4tzlich.",
            ),
            CareStep(
                "L\u00E4ngere Betreuung organisieren",
                "Danach greift Art.\u00A0324a OR (Lohnfortzahlung nach Dienstjahr). Gleichzeitig " +
                    "Ersatzbetreuung organisieren und GAV / Krankentaggeld pr\u00FCfen.",
            ),
            CareStep(
                "Familie in Deutschland absichern",
                "Die TK-Familienversicherung \u00FCbernimmt Arzt- und Krankenhauskosten der Familie, " +
                    "aber keine Lohnfortzahlung f\u00FCr Sie. Ihr Lohn l\u00E4uft \u00FCber den " +
                    "Schweizer Arbeitgeber.",
            ),
        ),
        documents = listOf(
            "\u00C4rztliches Zeugnis der Mutter (DE oder CH)",
            "Arbeitsvertrag und GAV (mehr Tage m\u00F6glich)",
            "Nachweis \u00FCber eine allf\u00E4llige Krankentaggeld-Versicherung des Arbeitgebers",
            "Bei Zusatzfall: Kinderkrankenschein / \u00E4rztliches Zeugnis des kranken Kindes",
        ),
        moneySources = listOf(
            MoneySource(
                "Schweizer Arbeitgeber",
                "Lohnfortzahlung nach Art.\u00A0329h OR bzw. Art.\u00A0324a OR \u2013 Ihre Hauptabsicherung",
            ),
            MoneySource(
                "Sympany (KVG)",
                "Grundversicherung in der Schweiz \u2013 zahlt Arzt/Krankenhaus, keine Lohnversicherung",
            ),
            MoneySource(
                "TK (Familie)",
                "Freiwillige gesetzliche Familienversicherung \u2013 medizinische Kosten der Familie, " +
                    "kein deutsches Kinderkrankengeld und kein Pflegeunterst\u00FCtzungsgeld f\u00FCr Sie",
            ),
        ),
        warnings = listOf(
            "Als Grenzg\u00E4nger in der Schweiz haben Sie keinen Anspruch auf deutsches " +
                "Kinderkrankengeld (\u00A7 45 SGB V) und kein Pflegeunterst\u00FCtzungsgeld (\u00A7 44a SGB XI).",
            "Die TK-Familienversicherung Ihrer Familie ersetzt keine Lohnfortzahlung f\u00FCr Sie.",
            "3 Tage sind das gesetzliche Minimum \u2013 Arbeitsvertrag und GAV bieten oft mehr.",
        ),
        employerTemplate = "Guten Tag [Name],\n\n" +
            "ich kann heute nicht zur Arbeit kommen, weil meine Partnerin erkrankt ist und ich " +
            "unsere Kinder betreuen muss.\n\n" +
            "Ich mache die Freistellung nach Art. 36 Abs. 3 ArG und die bezahlte Freistellung nach " +
            "Art. 329h OR geltend. Ein Arztzeugnis meiner Partnerin reiche ich nach.\n\n" +
            "Freundliche Gr\u00FCsse\n[Ihr Name]",
    )
}

object CareRulesProvider {
    fun forCountry(country: WorkCountry): CareRules = when (country) {
        WorkCountry.GERMANY -> GermanCareRules
        WorkCountry.SWITZERLAND -> SwissCrossBorderRules
    }
}

package com.kindkrank.app.data

/**
 * Wo arbeitet der Nutzer? Der Wohnort ist in beiden F\u00E4llen Deutschland.
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

/**
 * Familienstand der erkrankten Betreuungsperson.
 *
 * Wichtig f\u00FCr Grenzg\u00E4nger in die Schweiz: Art. 36 ArG und Art. 329h OR erkennen
 * eine nicht verheiratete Partnerin nur als nahe Angeh\u00F6rige an, wenn seit mindestens
 * 5 Jahren ein gemeinsamer Haushalt besteht (SECO-Wegleitung zu Art. 36 ArG).
 * In Deutschland gibt es diese Frist nicht (\u00A7 7 Abs. 3 PflegeZG).
 */
enum class PartnerStatus(
    val label: String,
    val chipLabel: String,
) {
    MARRIED(
        label = "Verheiratet",
        chipLabel = "Verheiratet",
    ),
    UNMARRIED_FIVE_YEARS(
        label = "Nicht verheiratet \u2013 seit mind. 5 Jahren gemeinsamer Haushalt",
        chipLabel = "Nicht verheiratet, \u2265 5 Jahre",
    ),
    UNMARRIED_UNDER_FIVE_YEARS(
        label = "Nicht verheiratet \u2013 seit weniger als 5 Jahren gemeinsamer Haushalt",
        chipLabel = "Nicht verheiratet, < 5 Jahre",
    );

    val isMarried: Boolean get() = this == MARRIED

    /** Erf\u00FCllt die schweizerische 5-Jahres-Voraussetzung (Ehe z\u00E4hlt immer). */
    val meetsFiveYearRule: Boolean get() = this != UNMARRIED_UNDER_FIVE_YEARS

    companion object {
        fun fromIndex(index: Int): PartnerStatus =
            values().getOrElse(index) { MARRIED }
    }
}

data class LegalBasis(
    val label: String,
    val text: String,
    val links: List<LegalLink> = emptyList(),
)

data class CareStep(val title: String, val text: String)

data class MoneySource(val label: String, val text: String)

/** Fertige Arbeitgeber-Textvorlage; der passende Text wird nach Familienstand gew\u00E4hlt.
 *  [note] erscheint als zus\u00E4tzlicher Hinweis unter der Vorlage. */
data class EmployerTemplate(val label: String, val text: String, val note: String? = null)

/**
 * Fertig aufbereiteter Anspruch f\u00FCr die Anzeige im Wizard.
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
    val employerTemplates: List<EmployerTemplate>,
)

interface CareRules {
    val country: WorkCountry
    fun caregiverSick(partnerStatus: PartnerStatus): Entitlement
}

/**
 * Arbeit in Deutschland, Kind gesund, Betreuungsperson (z. B. Mutter) krank.
 *
 * Wichtig: Das ist KEIN Kinderkrankengeld-Fall. Eine AU f\u00FCr den gesunden Vater gibt es nicht.
 * Der Familienstand \u00E4ndert hier nur die Anrede/Textvorlage \u2013 die 5-Jahres-Regel
 * gibt es im deutschen Recht nicht.
 */
object GermanCareRules : CareRules {
    override val country = WorkCountry.GERMANY

    override fun caregiverSick(partnerStatus: PartnerStatus): Entitlement {
        val married = partnerStatus.isMarried

        val template = if (married) {
            EmployerTemplate(
                label = "Verheiratet",
                text = "Guten Tag [Name],\n\n" +
                    "ich kann heute leider nicht zur Arbeit kommen, da meine Ehefrau " +
                    "krankheitsbedingt nicht in der Lage ist, unsere Kinder zu betreuen. Ich muss " +
                    "daher die notwendige Betreuung unserer Kinder \u00FCbernehmen. Eine andere " +
                    "Betreuung ist kurzfristig nicht verf\u00FCgbar.\n\n" +
                    "Ich mache meinen Anspruch auf kurzzeitige Arbeitsverhinderung nach " +
                    "\u00A7 2 PflegeZG geltend. Ein \u00E4rztliches Zeugnis \u00FCber die Erkrankung " +
                    "meiner Ehefrau reiche ich nach.\n\nViele Gr\u00FC\u00DFe\n[Ihr Name]",
            )
        } else {
            EmployerTemplate(
                label = "Nicht verheiratet",
                text = "Guten Tag [Name],\n\n" +
                    "ich kann heute leider nicht zur Arbeit kommen, da meine Partnerin " +
                    "krankheitsbedingt nicht in der Lage ist, unsere Kinder zu betreuen. Ich muss " +
                    "daher die notwendige Betreuung unserer Kinder \u00FCbernehmen. Eine andere " +
                    "Betreuung ist kurzfristig nicht verf\u00FCgbar.\n\n" +
                    "Ich mache meinen Anspruch auf kurzzeitige Arbeitsverhinderung nach " +
                    "\u00A7 2 PflegeZG geltend. Ein \u00E4rztliches Zeugnis \u00FCber die Erkrankung " +
                    "meiner Partnerin reiche ich nach.\n\nViele Gr\u00FC\u00DFe\n[Ihr Name]",
                note = "In Deutschland gibt es keine 5-Jahres-Frist: \u00A7 7 Abs. 3 PflegeZG " +
                    "erkennt auch die Partnerin einer ehe\u00E4hnlichen Gemeinschaft an \u2013 " +
                    "unabh\u00E4ngig davon, wie lange Sie bereits zusammenwohnen. Die 5-Jahres-Regel " +
                    "gilt nur im Schweizer Recht.",
            )
        }

        val warnings = buildList {
            add(
                "Eine Krankschreibung (AU) f\u00FCr Sie als gesunden Vater ist nicht m\u00F6glich. " +
                    "Ein Arzt darf Sie nicht krankschreiben, nur weil die Mutter krank ist.",
            )
            add(
                "\u00A7 616 BGB ist in vielen Arbeits- und Tarifvertr\u00E4gen ausgeschlossen \u2013 " +
                    "pr\u00FCfen Sie Ihren Vertrag.",
            )
            add(
                "Wird das Kind zus\u00E4tzlich krank, wechseln Sie in den Kinderkrankengeld-Fall " +
                    "(\u00A7 45 SGB V, Muster\u00A021).",
            )
            if (!married) {
                add(
                    "Nicht verheiratet: In Deutschland gen\u00FCgt eine ehe\u00E4hnliche Gemeinschaft " +
                        "(\u00A7 7 Abs. 3 PflegeZG). Eine 5-Jahres-Frist wie in der Schweiz gibt es " +
                        "hier nicht.",
                )
            }
        }

        return Entitlement(
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
                    links = listOf(LawLinks.BGB_275),
                ),
                LegalBasis(
                    "\u00A7 616 BGB",
                    "Unvorhergesehene Erkrankung eines nahen Angeh\u00F6rigen: ca. 5 Arbeitstage pro " +
                        "Jahr gelten als verh\u00E4ltnism\u00E4\u00DFig nicht erhebliche Zeit. Achtung: " +
                        "in vielen Arbeits- und Tarifvertr\u00E4gen ausgeschlossen.",
                    links = listOf(LawLinks.BGB_616),
                ),
                LegalBasis(
                    "\u00A7 2 PflegeZG / \u00A7 44a SGB XI",
                    "Kurzzeitige Arbeitsverhinderung bei akuter Pflegesituation: 10 Arbeitstage pro " +
                        "Kalenderjahr plus Pflegeunterst\u00FCtzungsgeld der Pflegekasse der Mutter. " +
                        "Ein Pflegegrad ist nicht n\u00F6tig \u2013 \u201Evoraussichtlich pflegebed\u00FCrftig\u201C gen\u00FCgt.",
                    links = listOf(LawLinks.PFLEGEZG_2, LawLinks.SGB_XI_44A),
                ),
                LegalBasis(
                    "\u00A7 45 SGB V",
                    "Wird das Kind zus\u00E4tzlich krank, greift Kinderkrankengeld \u2013 dann ist " +
                        "auch \u201Ekeine andere Person im Haushalt\u201C erf\u00FCllt, weil die Mutter ausf\u00E4llt.",
                    links = listOf(LawLinks.SGB_V_45),
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
            warnings = warnings,
            employerTemplates = listOf(template),
        )
    }
}

/**
 * Grenzg\u00E4nger: Wohnort Deutschland, Arbeitgeber in der Schweiz.
 *
 * Arbeitsrecht und Lohnfortzahlung richten sich nach Schweizer Recht. Die deutsche
 * Familienversicherung (z. B. TK) deckt medizinische Kosten, nicht den Lohn des Vaters.
 *
 * Der Familienstand ist hier entscheidend: Nur Ehepartner und nicht verheiratete
 * Partner mit mindestens 5 Jahren gemeinsamem Haushalt gelten als nahe Angeh\u00F6rige.
 */
object SwissCrossBorderRules : CareRules {
    override val country = WorkCountry.SWITZERLAND

    private const val FIVE_YEAR_RULE =
        "Art.\u00A036 ArG und Art.\u00A0329h OR erkennen eine nicht verheiratete Partnerin " +
            "nur als nahe Angeh\u00F6rige an, wenn Sie seit mindestens 5 Jahren einen gemeinsamen " +
            "Haushalt f\u00FChren (SECO-Wegleitung zu Art.\u00A036 ArG)."

    override fun caregiverSick(partnerStatus: PartnerStatus): Entitlement =
        if (partnerStatus.meetsFiveYearRule) {
            entitledSwiss(partnerStatus)
        } else {
            notEntitledSwiss()
        }

    private fun entitledSwiss(partnerStatus: PartnerStatus): Entitlement {
        val married = partnerStatus.isMarried

        val template = if (married) {
            EmployerTemplate(
                label = "Verheiratet",
                text = "Guten Tag [Name],\n\n" +
                    "ich kann heute leider nicht zur Arbeit kommen, da meine Ehefrau " +
                    "krankheitsbedingt nicht in der Lage ist, unsere Kinder zu betreuen. Ich muss " +
                    "daher die notwendige Betreuung unserer Kinder \u00FCbernehmen.\n\n" +
                    "Ich mache meinen Anspruch auf Betreuungsurlaub gem\u00E4ss Art. 36 Abs. 3 und 4 " +
                    "ArG in Verbindung mit Art. 324a OR sowie gegebenenfalls Art. 329h OR " +
                    "geltend.\n\n" +
                    "Ein \u00E4rztliches Zeugnis \u00FCber die Erkrankung meiner Ehefrau reiche ich " +
                    "nach.\n\nFreundliche Gr\u00FCsse\n[Ihr Name]",
            )
        } else {
            EmployerTemplate(
                label = "Nicht verheiratet",
                text = "Guten Tag [Name],\n\n" +
                    "ich kann heute leider nicht zur Arbeit kommen, da meine Partnerin " +
                    "krankheitsbedingt nicht in der Lage ist, unsere Kinder zu betreuen. Ich muss " +
                    "daher die notwendige Betreuung unserer Kinder \u00FCbernehmen.\n\n" +
                    "Ich mache meinen Anspruch auf Betreuungsurlaub gem\u00E4ss Art. 36 Abs. 3 und 4 " +
                    "ArG in Verbindung mit Art. 324a OR sowie gegebenenfalls Art. 329h OR " +
                    "geltend.\n\n" +
                    "Ein \u00E4rztliches Zeugnis \u00FCber die Erkrankung meiner Partnerin reiche ich " +
                    "nach.\n\nFreundliche Gr\u00FCsse\n[Ihr Name]",
                note = "5-Jahres-Voraussetzung erf\u00FCllt: Sie sind nicht verheiratet, f\u00FChren aber " +
                    "seit mindestens 5 Jahren einen gemeinsamen Haushalt. Damit gilt Ihre Partnerin " +
                    "als nahe Angeh\u00F6rige \u2013 der Betreuungsurlaub nach Art.\u00A036 ArG und " +
                    "Art.\u00A0329h OR steht Ihnen zu.",
            )
        }

        val warnings = buildList {
            add(
                "Als Grenzg\u00E4nger in der Schweiz haben Sie keinen Anspruch auf deutsches " +
                    "Kinderkrankengeld (\u00A7 45 SGB V) und kein Pflegeunterst\u00FCtzungsgeld (\u00A7 44a SGB XI).",
            )
            add(
                if (married) {
                    "Als Ehepartner gilt Ihre Frau ohne Wartezeit als nahe Angeh\u00F6rige."
                } else {
                    "Nicht verheiratet: Die 5-Jahres-Voraussetzung ist erf\u00FCllt \u2013 halten Sie " +
                        "einen Nachweis (z. B. gemeinsame Meldebescheinigung) bereit."
                },
            )
            add("Die TK-Familienversicherung Ihrer Familie ersetzt keine Lohnfortzahlung f\u00FCr Sie.")
            add("3 Tage sind das gesetzliche Minimum \u2013 Arbeitsvertrag und GAV bieten oft mehr.")
        }

        return coreEntitlement(
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
                    links = listOf(LawLinks.ARG_36),
                ),
                LegalBasis(
                    "Art. 329h OR",
                    "Bezahlte Freistellung f\u00FCr die Betreuung eines kranken Kindes: 100\u00A0% Lohn, " +
                        "3 Tage pro Ereignis, maximal 10 Tage pro Dienstjahr.",
                    links = listOf(LawLinks.OR_329H),
                ),
                LegalBasis(
                    "Art. 324a OR",
                    "Bei l\u00E4ngerer Betreuung: Lohnfortzahlung nach Dienstjahr-Skala, weil die " +
                        "Betreuung eine gesetzliche Pflicht ist (Art.\u00A0272/276 ZGB) \u2013 bis eine " +
                        "Ersatzbetreuung organisiert ist.",
                    links = listOf(LawLinks.OR_324A, LawLinks.ZGB_272_276),
                ),
                LegalBasis(
                    "Art. 36 ArG / Art. 329h OR \u2013 nahe Angeh\u00F6rige",
                    FIVE_YEAR_RULE + " Diese Voraussetzung ist bei Ihnen erf\u00FCllt.",
                    links = listOf(LawLinks.ARG_36, LawLinks.OR_329H),
                ),
                LegalBasis(
                    "GAV / Krankentaggeld",
                    "Viele Gesamtarbeitsvertr\u00E4ge oder Arbeitgeber bieten mehr als die gesetzlichen " +
                        "3 Tage. Pr\u00FCfen Sie Arbeitsvertrag und GAV.",
                ),
            ),
            documents = listOf(
                "\u00C4rztliches Zeugnis der Mutter (DE oder CH)",
                "Arbeitsvertrag und GAV (mehr Tage m\u00F6glich)",
                "Nachweis \u00FCber eine allf\u00E4llige Krankentaggeld-Versicherung des Arbeitgebers",
                "Bei nicht verheirateten Paaren: Nachweis des 5-j\u00E4hrigen gemeinsamen Haushalts",
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
            warnings = warnings,
            employerTemplates = listOf(template),
        )
    }

    private fun notEntitledSwiss(): Entitlement = coreEntitlement(
        dayLabel = "Kein Betreuungsurlaub",
        dayValue = "nur Art. 324a OR",
        paySource = "Keine 3 bezahlten Betreuungstage \u2013 nur Lohnfortzahlung nach " +
            "Art.\u00A0324a OR (nach Dienstjahr-Skala)",
        immediateAction = "Informieren Sie heute sofort Ihren Schweizer Arbeitgeber. Achtung: Da Sie " +
            "nicht verheiratet sind und seit weniger als 5 Jahren in einem gemeinsamen Haushalt " +
            "leben, gelten Sie arbeitsrechtlich nicht als nahe Angeh\u00F6rige. Der bezahlte " +
            "Betreuungsurlaub von 3 Tagen entf\u00E4llt \u2013 es bleibt die Lohnfortzahlung nach " +
            "Art.\u00A0324a OR.",
        legalBasis = listOf(
            LegalBasis(
                "Art. 324a OR",
                "Lohnfortzahlung bei unverschuldeter Arbeitsverhinderung aus gesetzlicher Pflicht " +
                    "(Betreuung des Kindes) \u2013 nach Dienstjahr-Skala. Das ist Ihre einzige " +
                    "gesetzliche Absicherung in dieser Konstellation.",
                links = listOf(LawLinks.OR_324A),
            ),
            LegalBasis(
                "Art. 36 ArG / Art. 329h OR \u2013 NICHT anwendbar",
                FIVE_YEAR_RULE + " Da diese 5 Jahre bei Ihnen noch nicht erreicht sind, entf\u00E4llt " +
                    "der Betreuungsurlaub nach Art.\u00A036 Abs.\u00A03 ArG und die bezahlte " +
                    "Freistellung nach Art.\u00A0329h OR.",
                links = listOf(LawLinks.ARG_36, LawLinks.OR_329H),
            ),
            LegalBasis(
                "GAV / Krankentaggeld",
                "Pr\u00FCfen Sie Arbeitsvertrag und GAV: Manche Arbeitgeber oder Gesamtarbeitsvertr\u00E4ge " +
                    "gew\u00E4hren freiwillig mehr als das Gesetz.",
            ),
        ),
        steps = listOf(
            CareStep(
                "Heute: Schweizer Arbeitgeber informieren",
                "Rufen Sie sofort an oder schreiben Sie eine E-Mail. Berufen Sie sich auf " +
                    "Art.\u00A0324a OR \u2013 nicht auf Art.\u00A036 ArG, den Sie in dieser Konstellation " +
                    "nicht beanspruchen k\u00F6nnen.",
            ),
            CareStep(
                "\u00C4rztliches Zeugnis der Mutter besorgen",
                "Ein Arztzeugnis aus Deutschland oder der Schweiz belegt, dass die Mutter nicht " +
                    "betreuen kann.",
            ),
            CareStep(
                "Lohnfortzahlung nach Art.\u00A0324a OR kl\u00E4ren",
                "Die H\u00F6he richtet sich nach dem Dienstjahr. Fragen Sie Ihren Arbeitgeber, wie viel " +
                    "Lohn in Ihrem Fall weiterl\u00E4uft.",
            ),
            CareStep(
                "Ersatzbetreuung organisieren",
                "Weil der bezahlte Betreuungsurlaub fehlt, ist eine Ersatzbetreuung (Gro\u00DFeltern, " +
                    "Babysitter, Kita) jetzt besonders wichtig. Pr\u00FCfen Sie GAV und Krankentaggeld.",
            ),
            CareStep(
                "Familie in Deutschland absichern",
                "Die TK-Familienversicherung \u00FCbernimmt Arzt- und Krankenhauskosten der Familie, " +
                    "aber keine Lohnfortzahlung f\u00FCr Sie.",
            ),
        ),
        documents = listOf(
            "\u00C4rztliches Zeugnis der Mutter (DE oder CH)",
            "Arbeitsvertrag und GAV (freiwillige Leistungen pr\u00FCfen)",
            "Nachweis \u00FCber eine allf\u00E4llige Krankentaggeld-Versicherung des Arbeitgebers",
            "Bei Zusatzfall: Kinderkrankenschein / \u00E4rztliches Zeugnis des kranken Kindes",
        ),
        moneySources = listOf(
            MoneySource(
                "Schweizer Arbeitgeber (Art. 324a OR)",
                "Lohnfortzahlung nach Dienstjahr-Skala \u2013 kein 3-Tage-Betreuungsurlaub",
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
            "5-Jahres-Regel nicht erf\u00FCllt: Ohne mindestens 5 Jahre gemeinsamen Haushalt " +
                "entf\u00E4llt der Betreuungsurlaub nach Art.\u00A036 ArG und Art.\u00A0329h OR.",
            "Als Grenzg\u00E4nger in der Schweiz haben Sie keinen Anspruch auf deutsches " +
                "Kinderkrankengeld (\u00A7 45 SGB V) und kein Pflegeunterst\u00FCtzungsgeld (\u00A7 44a SGB XI).",
            "Die TK-Familienversicherung Ihrer Familie ersetzt keine Lohnfortzahlung f\u00FCr Sie.",
            "Pr\u00FCfen Sie GAV und Arbeitsvertrag \u2013 manche Arbeitgeber gew\u00E4hren freiwillig mehr.",
        ),
        employerTemplates = listOf(
            EmployerTemplate(
                label = "Nicht verheiratet, < 5 Jahre",
                text = "Guten Tag [Name],\n\n" +
                    "ich kann heute leider nicht zur Arbeit kommen, da meine Partnerin " +
                    "krankheitsbedingt nicht in der Lage ist, unsere Kinder zu betreuen. Ich muss " +
                    "daher die notwendige Betreuung unserer Kinder \u00FCbernehmen.\n\n" +
                    "Ich mache meinen Anspruch auf Lohnfortzahlung bei unverschuldeter " +
                    "Arbeitsverhinderung gem\u00E4ss Art. 324a OR geltend.\n\n" +
                    "Ein \u00E4rztliches Zeugnis \u00FCber die Erkrankung meiner Partnerin reiche ich " +
                    "nach.\n\nFreundliche Gr\u00FCsse\n[Ihr Name]",
                note = FIVE_YEAR_RULE + " Da diese 5 Jahre noch nicht erreicht sind, entf\u00E4llt der " +
                    "bezahlte Betreuungsurlaub. Die Vorlage beruft sich deshalb bewusst nur auf " +
                    "Art.\u00A0324a OR.",
            ),
        ),
    )

    private fun coreEntitlement(
        dayLabel: String,
        dayValue: String,
        paySource: String,
        immediateAction: String,
        legalBasis: List<LegalBasis>,
        documents: List<String>,
        moneySources: List<MoneySource>,
        warnings: List<String>,
        employerTemplates: List<EmployerTemplate>,
        steps: List<CareStep> = defaultSteps(),
    ) = Entitlement(
        title = "Betreuungsperson krank \u2013 Grenzg\u00E4nger",
        subtitle = "Sie wohnen in Deutschland, arbeiten aber in der Schweiz. Ihr Kind ist gesund, " +
            "die Mutter / Betreuungsperson kann heute nicht betreuen.",
        dayLabel = dayLabel,
        dayValue = dayValue,
        paySource = paySource,
        immediateAction = immediateAction,
        legalBasis = legalBasis,
        steps = steps,
        documents = documents,
        moneySources = moneySources,
        warnings = warnings,
        employerTemplates = employerTemplates,
    )

    private fun defaultSteps(): List<CareStep> = listOf(
        CareStep(
            "Heute: Schweizer Arbeitgeber informieren",
            "Rufen Sie sofort an oder schreiben Sie eine E-Mail. Nennen Sie den Grund und " +
                "berufen Sie sich auf die passende Rechtsgrundlage.",
        ),
        CareStep(
            "\u00C4rztliches Zeugnis der Mutter besorgen",
            "Ein Arztzeugnis aus Deutschland oder der Schweiz ist g\u00FCltig. Es belegt, dass " +
                "die Mutter nicht betreuen kann.",
        ),
        CareStep(
            "Anspruch beim Arbeitgeber geltend machen",
            "Legen Sie die Vorlage aus dem vorherigen Schritt zugrunde und reichen Sie das " +
                "Zeugnis nach.",
        ),
        CareStep(
            "L\u00E4ngere Betreuung organisieren",
            "Ersatzbetreuung organisieren und GAV / Krankentaggeld pr\u00FCfen.",
        ),
        CareStep(
            "Familie in Deutschland absichern",
            "Die TK-Familienversicherung \u00FCbernimmt Arzt- und Krankenhauskosten der Familie, " +
                "aber keine Lohnfortzahlung f\u00FCr Sie.",
        ),
    )
}

object CareRulesProvider {
    fun forCountry(country: WorkCountry): CareRules = when (country) {
        WorkCountry.GERMANY -> GermanCareRules
        WorkCountry.SWITZERLAND -> SwissCrossBorderRules
    }
}

package com.kindkrank.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kindkrank.app.data.LawLinks
import com.kindkrank.app.ui.components.LegalLinksRow
import com.kindkrank.app.ui.components.ShareableInfoCard
import com.kindkrank.app.ui.theme.BackgroundBlue
import com.kindkrank.app.ui.theme.LightYellow
import com.kindkrank.app.ui.theme.PrimaryBlue
import com.kindkrank.app.ui.theme.PrimaryDark
import com.kindkrank.app.ui.theme.SurfaceWhite
import com.kindkrank.app.ui.theme.TextPrimary
import com.kindkrank.app.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrenzgaengerSchweizScreen(
    onGoHome: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Grenzgänger Schweiz") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue,
                    titleContentColor = SurfaceWhite,
                    navigationIconContentColor = SurfaceWhite,
                ),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundBlue)
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
        ) {
            Text(
                text = "\uD83C\uDDE8\uD83C\uDDED Arbeit in der Schweiz – Wohnen in Deutschland",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 28.sp,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Als Grenzgänger gelten besondere Regeln – hier erfahren Sie, was sich ändert.",
                fontSize = 15.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 22.sp,
            )
            Spacer(Modifier.height(20.dp))

            // Key difference warning card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = LightYellow),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(text = "\u26A0\uFE0F", fontSize = 22.sp)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Wichtiger Unterschied",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDark,
                            modifier = Modifier.padding(bottom = 6.dp),
                        )
                        Text(
                            text = "Das deutsche Kinderkrankengeld (\u00A7\u00A045 SGB\u00A0V) gilt in der Regel " +
                                "nicht f\u00FCr Grenzg\u00E4nger, die in der Schweiz arbeiten und dort versichert " +
                                "sind. Stattdessen greift Schweizer Arbeitsrecht: Art.\u00A036 Abs.\u00A03 ArG " +
                                "(Freistellung) und Art.\u00A0329h OR (bezahlte Freistellung, max. 10 Tage pro Dienstjahr).",
                            fontSize = 14.sp,
                            color = TextPrimary,
                            lineHeight = 21.sp,
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            LegalLinksRow(
                links = listOf(LawLinks.ARG_36, LawLinks.OR_329H, LawLinks.SGB_V_45),
            )
            Spacer(Modifier.height(16.dp))

            Text(
                text = "Ihr Ablauf als Grenzg\u00E4nger",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryDark,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(12.dp))

            GrenzgaengerStepCard(
                step = 1,
                title = "\uD83D\uDCF1 Schweizer Arbeitgeber sofort informieren",
                description = "Rufen Sie Ihren Arbeitgeber in der Schweiz noch heute an oder schreiben Sie eine " +
                    "E-Mail. Teilen Sie mit, dass Ihr Kind krank ist und Sie die Betreuung \u00FCbernehmen. " +
                    "Dieser Schritt ist identisch mit dem deutschen Prozess.",
            )
            Spacer(Modifier.height(12.dp))

            GrenzgaengerStepCard(
                step = 2,
                title = "\uD83E\uDE7A Arztbesuch (Deutschland oder Schweiz)",
                description = "Sie k\u00F6nnen einen deutschen Kinderarzt oder Hausarzt aufsuchen. In Deutschland " +
                    "erhalten Sie den Kinderkrankenschein (Muster\u00A021). Auch ein Schweizer Arztzeugnis ist " +
                    "g\u00FCltig. Das Attest dient als Nachweis gegen\u00FCber Ihrem Schweizer Arbeitgeber.",
            )
            Spacer(Modifier.height(12.dp))

            GrenzgaengerStepCard(
                step = 3,
                title = "\uD83D\uDCB6 Lohnfortzahlung durch Schweizer Arbeitgeber",
                description = "Nach Art.\u00A0329h OR haben Sie Anspruch auf bezahlte Freistellung von bis zu " +
                    "3 Tagen pro Ereignis, wenn Sie pers\u00F6nlich die Betreuung Ihres kranken Kindes " +
                    "\u00FCbernehmen m\u00FCssen (max. 10 Tage pro Dienstjahr). Die Freistellung selbst ergibt " +
                    "sich aus Art.\u00A036 Abs.\u00A03 ArG. Ihr Schweizer Arbeitgeber zahlt den Lohn weiter \u2013 " +
                    "die Krankenkasse leistet keine Lohnzahlung.",
            )
            Spacer(Modifier.height(12.dp))

            // Extra info: what happens after 3 days
            ShareableInfoCard(
                text = "Nach den 3 Tagen \u2013 was dann?\n" +
                    "Nach Aufbrauchen der 3 bezahlten Tage gilt Art. 324a OR: Lohnfortzahlung nach " +
                    "Dienstjahr-Skala, weil die Betreuung eine gesetzliche Pflicht ist. Pruefen Sie " +
                    "zudem Arbeitsvertrag und GAV \u2013 viele bieten mehr als die gesetzlichen 3 Tage. " +
                    "Organisieren Sie parallel eine Ersatzbetreuung. Ein deutsches Kinderkrankengeld " +
                    "greift als Grenzgaenger mit Schweizer Arbeitgeber nicht.",
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(text = "\uD83D\uDCA1", fontSize = 18.sp)
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Nach den 3 Tagen – was dann?")
                            }
                            append("\nNach Aufbrauchen der 3 bezahlten Tage gilt Art.\u00A0324a OR: " +
                                "Lohnfortzahlung nach Dienstjahr-Skala, weil die Betreuung eine gesetzliche " +
                                "Pflicht ist. Pr\u00FCfen Sie zudem Arbeitsvertrag und GAV \u2013 viele bieten " +
                                "mehr als die gesetzlichen 3 Tage. Organisieren Sie parallel eine " +
                                "Ersatzbetreuung. Ein deutsches Kinderkrankengeld greift als Grenzg\u00E4nger " +
                                "mit Schweizer Arbeitgeber nicht.")
                        },
                        fontSize = 13.sp,
                        color = TextPrimary,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(end = 20.dp),
                    )
                }
            }
            Spacer(Modifier.height(12.dp))

            // 5-year rule for unmarried couples (affects who can claim the care leave)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = LightYellow),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(text = "\uD83D\uDC65", fontSize = 22.sp)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Nicht verheiratet? Die 5-Jahres-Regel",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDark,
                            modifier = Modifier.padding(bottom = 6.dp),
                        )
                        Text(
                            text = "Will Ihre Partnerin oder Ihr Partner die Betreuung übernehmen " +
                                "und dafür den bezahlten Betreuungsurlaub (Art.\u00A036 Abs.\u00A03 ArG " +
                                "/ Art.\u00A0329h OR) beanspruchen, muss sie oder er seit mindestens " +
                                "5 Jahren mit dem Kind in einem gemeinsamen Haushalt leben. Fehlen " +
                                "diese 5 Jahre, bleibt nur die Lohnfortzahlung nach Art.\u00A0324a OR " +
                                "\u2013 ohne die 3 bezahlten Betreuungstage. Für verheiratete Paare " +
                                "gilt die Frist nicht.",
                            fontSize = 14.sp,
                            color = TextPrimary,
                            lineHeight = 21.sp,
                        )
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            LegalLinksRow(
                links = listOf(LawLinks.ARG_36, LawLinks.OR_329H, LawLinks.OR_324A),
            )
            Spacer(Modifier.height(12.dp))

            GrenzgaengerStepCard(
                step = 4,
                title = "\uD83C\uDFE5 Familie in Deutschland absichern",
                description = "Die deutsche Familienversicherung (z.\u00A0B. TK) \u00FCbernimmt Arzt- und " +
                    "Krankenhauskosten Ihrer Familie, aber keine Lohnfortzahlung f\u00FCr Sie. Als " +
                    "Grenzg\u00E4nger mit Schweizer Arbeitgeber haben Sie keinen Anspruch auf deutsches " +
                    "Kinderkrankengeld (\u00A7\u00A045 SGB\u00A0V). Ihr Lohn l\u00E4uft \u00FCber den Schweizer " +
                    "Arbeitgeber (Art.\u00A0329h OR / Art.\u00A0324a OR).",
            )
            Spacer(Modifier.height(16.dp))

            // Swiss law info box
            ShareableInfoCard(
                text = "Rechtliche Grundlage Schweiz:\n" +
                    "Art. 36 Abs. 3 ArG: Freistellung von der Arbeit, bis zu 3 Arbeitstage pro Ereignis; fuer Kinder keine Jahresgrenze (Abs. 4).\n" +
                    "Art. 329h OR: Bezahlte Freistellung fuer die Betreuung eines kranken Kindes, 100 % Lohn, 3 Tage pro Ereignis, max. 10 Tage pro Dienstjahr.\n" +
                    "Art. 324a OR: Laengere Lohnfortzahlung nach Dienstjahr-Skala.\n" +
                    "Kein Kinderkrankengeld: Die Schweizer Krankenkasse (KVG/LAMal) zahlt keine Lohnersatzleistung fuer erkrankte Kinder.\n" +
                    "Mehr Tage moeglich: Viele Gesamtarbeitsvertraege (GAV) bieten mehr als 3 Tage an.",
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "\u2696\uFE0F Rechtliche Grundlage Schweiz",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark,
                        modifier = Modifier.padding(end = 20.dp),
                    )
                    Spacer(Modifier.height(10.dp))
                    listOf(
                        buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Art.\u00A036 Abs.\u00A03 ArG: ")
                            }
                            append(
                                "Freistellung von der Arbeit, bis zu 3 Arbeitstage pro Ereignis. " +
                                    "F\u00FCr Kinder gilt keine Jahresgrenze (Abs.\u00A04).",
                            )
                        },
                        buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Art.\u00A0329h OR: ")
                            }
                            append(
                                "Bezahlte Freistellung f\u00FCr die Betreuung eines kranken Kindes " +
                                    "\u2013 100\u00A0% Lohn, 3 Tage pro Ereignis, max. 10 Tage pro Dienstjahr.",
                            )
                        },
                        buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Kein Kinderkrankengeld: ")
                            }
                            append(
                                "Es gibt keine separate Leistung der Schweizer Krankenkasse " +
                                    "(KVG/LAMal) f\u00FCr erkrankte Kinder des Arbeitnehmers.",
                            )
                        },
                        buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Mehr Tage m\u00F6glich: ")
                            }
                            append(
                                "Viele Gesamtarbeitsvertr\u00E4ge (GAV) bieten mehr als 3 Tage an \u2013 " +
                                    "pr\u00FCfen Sie Ihren Arbeitsvertrag oder GAV.",
                            )
                        },
                    ).forEach { text ->
                        Text(
                            text = text,
                            fontSize = 14.sp,
                            color = TextPrimary,
                            lineHeight = 21.sp,
                            modifier = Modifier.padding(bottom = 8.dp),
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            LegalLinksRow(
                links = listOf(
                    LawLinks.ARG_36,
                    LawLinks.OR_329H,
                    LawLinks.OR_324A,
                    LawLinks.ZGB_272_276,
                ),
            )
            Spacer(Modifier.height(16.dp))

            // Comparison table
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "\uD83D\uDD0D Vergleich Deutschland \u2194 Schweiz",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark,
                        modifier = Modifier.padding(bottom = 12.dp),
                    )
                    ComparisonRow(
                        label = "Rechtsgrundlage",
                        de = "\u00A7\u00A045 SGB\u00A0V",
                        ch = "Art.\u00A0329h OR + Art.\u00A036 ArG",
                    )
                    Spacer(Modifier.height(10.dp))
                    ComparisonRow(
                        label = "Anspruch",
                        de = "10 Tage/Kind/Jahr (20 bei Alleinerz.)",
                        ch = "3 Tage/Ereignis, max. 10/Dienstjahr",
                    )
                    Spacer(Modifier.height(10.dp))
                    ComparisonRow(
                        label = "Wer zahlt",
                        de = "Krankenkasse (~90\u00A0% Nettolohn)",
                        ch = "Arbeitgeber (voller Lohn)",
                    )
                    Spacer(Modifier.height(10.dp))
                    ComparisonRow(
                        label = "Altersgrenze Kind",
                        de = "Unter 12 Jahre",
                        ch = "Unter 15 Jahre",
                    )
                }
            }
            Spacer(Modifier.height(20.dp))

            // Disclaimer
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = LightYellow),
            ) {
                Row(modifier = Modifier.padding(14.dp)) {
                    Text(text = "\u26A0\uFE0F", fontSize = 16.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Diese Informationen sind allgemeiner Natur. Grenzg\u00E4nger-Situationen " +
                            "k\u00F6nnen komplex sein \u2013 bitte wenden Sie sich bei konkreten Fragen an " +
                            "Ihren Arbeitgeber, Ihre Krankenkasse oder einen Rechtsanwalt.",
                        fontSize = 13.sp,
                        color = Color(0xFF555555),
                        lineHeight = 20.sp,
                    )
                }
            }
            Spacer(Modifier.height(20.dp))

            Button(
                onClick = onGoHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            ) {
                Text(
                    text = "\u21A9 Zur\u00FCck zur Startseite",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun GrenzgaengerStepCard(step: Int, title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = step.toString(),
                    color = SurfaceWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.width(14.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDark,
                    modifier = Modifier.padding(bottom = 6.dp),
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = TextPrimary,
                    lineHeight = 21.sp,
                )
            }
        }
    }
}

@Composable
private fun ComparisonRow(label: String, de: String, ch: String) {
    Column {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondary,
        )
        Spacer(Modifier.height(4.dp))
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "\uD83C\uDDE9\uD83C\uDDEA Deutschland",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(text = de, fontSize = 13.sp, color = TextPrimary, lineHeight = 19.sp)
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "\uD83C\uDDE8\uD83C\uDDED Schweiz",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(text = ch, fontSize = 13.sp, color = TextPrimary, lineHeight = 19.sp)
            }
        }
    }
}

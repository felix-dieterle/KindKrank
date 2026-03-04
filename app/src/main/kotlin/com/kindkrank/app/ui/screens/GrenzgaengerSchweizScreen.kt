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
import com.kindkrank.app.ui.theme.BackgroundBlue
import com.kindkrank.app.ui.theme.InfoBlue
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
                                "sind. Stattdessen greift das Schweizer Arbeitsrecht (OR Art.\u00A036 Abs.\u00A03).",
                            fontSize = 14.sp,
                            color = TextPrimary,
                            lineHeight = 21.sp,
                        )
                    }
                }
            }
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
                description = "Nach Schweizer Recht (OR Art.\u00A036 Abs.\u00A03) haben Sie Anspruch auf bezahlte " +
                    "Freistellung von bis zu 3 Tagen pro Erkrankung, wenn Sie pers\u00F6nlich die Betreuung Ihres " +
                    "Kindes (unter 15 Jahren) \u00FCbernehmen m\u00FCssen. Ihr Schweizer Arbeitgeber zahlt Ihren " +
                    "Lohn weiter \u2013 es gibt keine separate Leistung der Krankenkasse.",
            )
            Spacer(Modifier.height(12.dp))

            GrenzgaengerStepCard(
                step = 4,
                title = "\uD83C\uDFE5 Pr\u00FCfen: Bin ich in Deutschland krankenversichert?",
                description = "Sind Sie \u00FCber eine Befreiung von der Schweizer Versicherungspflicht in " +
                    "Deutschland gesetzlich krankenversichert (GKV)? Dann k\u00F6nnen Sie ggf. das deutsche " +
                    "Kinderkrankengeld (\u00A7\u00A045 SGB\u00A0V) zus\u00E4tzlich beantragen. Kontaktieren Sie " +
                    "Ihre deutsche Krankenkasse, um dies zu pr\u00FCfen.",
            )
            Spacer(Modifier.height(16.dp))

            // Swiss law info box
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = InfoBlue),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "\u2696\uFE0F Rechtliche Grundlage Schweiz",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark,
                    )
                    Spacer(Modifier.height(10.dp))
                    listOf(
                        buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("OR Art.\u00A036 Abs.\u00A03: ")
                            }
                            append(
                                "Bis zu 3 Tage bezahlte Freistellung pro Erkrankung eines Kindes " +
                                    "unter 15 Jahren.",
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
                        ch = "OR Art.\u00A036 Abs.\u00A03",
                    )
                    Spacer(Modifier.height(10.dp))
                    ComparisonRow(
                        label = "Anspruch",
                        de = "10 Tage/Kind/Jahr (20 bei Alleinerz.)",
                        ch = "Bis zu 3 Tage pro Erkrankung",
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

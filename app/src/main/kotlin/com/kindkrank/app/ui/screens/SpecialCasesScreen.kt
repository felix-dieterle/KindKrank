package com.kindkrank.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kindkrank.app.ui.theme.BackgroundBlue
import com.kindkrank.app.ui.theme.LightYellow
import com.kindkrank.app.ui.theme.PrimaryBlue
import com.kindkrank.app.ui.theme.PrimaryDark
import com.kindkrank.app.ui.theme.SurfaceWhite
import com.kindkrank.app.ui.theme.TextPrimary
import com.kindkrank.app.ui.theme.TextSecondary
import com.kindkrank.app.ui.theme.WarningAmber

private data class SpecialCase(val emoji: String, val title: String, val description: String)

private val SPECIAL_CASES = listOf(
    SpecialCase(
        emoji = "🏥",
        title = "Stationärer Krankenhausaufenthalt",
        description = "Auch beim Krankenhausaufenthalt des Kindes haben Eltern Anspruch auf Kinderkranktage. " +
            "Die Bescheinigung stellt das Krankenhaus aus. Der Anspruch besteht unabhängig vom Alter des Kindes, " +
            "wenn ein Elternteil medizinisch notwendig mitaufgenommen wird.",
    ),
    SpecialCase(
        emoji = "♿",
        title = "Kind mit Behinderung",
        description = "Bei Kindern mit Behinderung, die auf Betreuung angewiesen sind, gilt keine Altersgrenze " +
            "(keine 12-Jahres-Grenze). Der Anspruch auf Kinderkranktage gilt unbegrenzt.",
    ),
    SpecialCase(
        emoji = "👶",
        title = "Mehrere Kinder",
        description = "Für jedes Kind unter 12 Jahren haben Sie 10 (bzw. 20 bei Alleinerziehenden) Tage Anspruch, " +
            "jedoch maximal 25 (bzw. 50) Tage insgesamt. Beispiel: 2 Kinder = 20 Tage je Elternteil, aber max. 25.",
    ),
    SpecialCase(
        emoji = "📅",
        title = "Übertragung von Tagen",
        description = "Kinderkranktage können zwischen Elternteilen übertragen werden, wenn ein Elternteil " +
            "seinen Anspruch nicht vollständig nutzt. Bitte fragen Sie bei Ihrer Krankenkasse nach, " +
            "da die Regelungen sich ändern können.",
    ),
    SpecialCase(
        emoji = "🏢",
        title = "Arbeitgeber verweigert Freistellung",
        description = "Der Arbeitgeber ist verpflichtet, die Freistellung zu gewähren (§ 45 SGB V). " +
            "Verweigert er dies, haben Arbeitnehmer trotzdem Anspruch – konsultieren Sie bei Problemen " +
            "einen Fachanwalt für Arbeitsrecht oder den Betriebsrat.",
    ),
    SpecialCase(
        emoji = "💰",
        title = "Beamte & Selbstständige",
        description = "Beamte erhalten in der Regel Sonderurlaub über den Dienstherren. " +
            "Selbstständige ohne gesetzliche Krankenversicherung haben keinen Anspruch auf " +
            "Kinderkrankengeld, außer bei freiwilliger GKV mit Krankengeld-Anspruch.",
    ),
    SpecialCase(
        emoji = "🔄",
        title = "Kinderkranktage aufgebraucht",
        description = "Sollte das Kind länger krank sein als die verbleibenden Kinderkranktage, muss " +
            "Urlaub genommen, unbezahlter Urlaub beantragt oder ein anderer Elternteil die Betreuung " +
            "übernehmen. In Ausnahmesituationen können Bundesländer Sonderregelungen einführen.",
    ),
    SpecialCase(
        emoji = "📋",
        title = "Kinderkrankenschein nachträglich",
        description = "Der Kinderkrankenschein sollte beim ersten Arztbesuch ausgestellt werden. " +
            "Eine nachträgliche Ausstellung ist möglich, aber komplizierter – manche Krankenkassen " +
            "akzeptieren ihn dann nicht für alle Tage.",
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialCasesScreen(onGoHome: () -> Unit, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sonderfälle") },
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
                text = "Sonderfälle & Ausnahmen",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Wichtige Sonderfälle rund um das Thema Kinderkranktage.",
                fontSize = 15.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(20.dp))

            SPECIAL_CASES.forEach { sc ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Text(text = sc.emoji, fontSize = 28.sp)
                        Spacer(Modifier.width(14.dp))
                        Column {
                            Text(
                                text = sc.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryDark,
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = sc.description,
                                fontSize = 14.sp,
                                color = TextPrimary,
                                lineHeight = 21.sp,
                            )
                        }
                    }
                }
            }

            // Disclaimer
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = LightYellow),
            ) {
                Row(modifier = Modifier.padding(14.dp)) {
                    Text(text = "⚠\uFE0F", fontSize = 16.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Diese Informationen sind allgemeiner Natur und können sich ändern. " +
                            "Bitte wenden Sie sich bei konkreten Fragen an Ihre Krankenkasse " +
                            "oder einen Rechtsanwalt.",
                        fontSize = 13.sp,
                        color = Color(0xFF555555),
                        lineHeight = 20.sp,
                    )
                }
            }

            Button(
                onClick = onGoHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            ) {
                Text(
                    text = "↩ Zurück zur Startseite",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

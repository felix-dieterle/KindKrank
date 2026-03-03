package com.kindkrank.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kindkrank.app.ui.theme.ErrorRed
import com.kindkrank.app.ui.theme.LightYellow
import com.kindkrank.app.ui.theme.PrimaryBlue
import com.kindkrank.app.ui.theme.SurfaceWhite
import com.kindkrank.app.ui.theme.TextPrimary
import com.kindkrank.app.ui.theme.TextSecondary

@Composable
fun DisclaimerScreen(onAccepted: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightYellow)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "⚠\uFE0F Rechtlicher Hinweis",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = ErrorRed,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Haftungsausschluss",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondary,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
            ) {
                DisclaimerParagraph(
                    text = buildAnnotatedString {
                        append("Diese App bietet ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("allgemeine Informationen") }
                        append(
                            " zur Vorgehensweise bei Erkrankung eines Kindes in Deutschland. " +
                                "Sie ersetzt "
                        )
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("keine Rechts- oder Steuerberatung") }
                        append(" und stellt keine rechtliche Empfehlung dar.")
                    },
                )
                DisclaimerParagraph(
                    text = buildAnnotatedString {
                        append(
                            "Alle Angaben wurden sorgfältig zusammengestellt, jedoch können sich gesetzliche " +
                                "Regelungen, Krankenkassenrichtlinien oder Unternehmensregelungen jederzeit ändern. " +
                                "Für die Richtigkeit, Vollständigkeit und Aktualität der bereitgestellten " +
                                "Informationen wird "
                        )
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("keine Haftung übernommen") }
                        append(".")
                    },
                )
                DisclaimerParagraph(
                    text = buildAnnotatedString {
                        append(
                            "Die Nutzung dieser App erfolgt auf eigene Verantwortung. Für Entscheidungen, " +
                                "die auf Basis der hier dargestellten Informationen getroffen werden, " +
                                "übernehmen Entwickler und Betreiber dieser App "
                        )
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("keinerlei Haftung") }
                        append(
                            ". Bei Unsicherheiten wenden Sie sich bitte an Ihre Krankenkasse, " +
                                "Ihren Arbeitgeber oder einen Fachanwalt für Arbeitsrecht."
                        )
                    },
                )
                DisclaimerParagraph(
                    text = buildAnnotatedString {
                        append("Diese App unterstützt Sie dabei, den ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("offiziellen Weg") }
                        append(
                            " zu gehen. Ein bewusstes Umgehen gesetzlicher Regelungen " +
                                "wird ausdrücklich nicht empfohlen."
                        )
                    },
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Gemäß § 675 BGB und den allgemeinen Grundsätzen der Haftungsbeschränkung " +
                        "für unentgeltliche Informationsleistungen ist eine Haftung für die Inhalte " +
                        "dieser App ausgeschlossen, soweit keine vorsätzliche oder grob fahrlässige " +
                        "Pflichtverletzung vorliegt.",
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF888888),
                    lineHeight = 18.sp,
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onAccepted,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
        ) {
            Text(
                text = "Verstanden – App starten",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun DisclaimerParagraph(text: androidx.compose.ui.text.AnnotatedString) {
    Text(
        text = text,
        fontSize = 15.sp,
        color = TextPrimary,
        lineHeight = 24.sp,
        modifier = Modifier.padding(bottom = 14.dp),
    )
}

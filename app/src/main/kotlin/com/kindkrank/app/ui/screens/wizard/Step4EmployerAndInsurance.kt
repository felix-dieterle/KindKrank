package com.kindkrank.app.ui.screens.wizard

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kindkrank.app.data.KRANKENKASSEN
import com.kindkrank.app.data.Krankenkasse
import com.kindkrank.app.ui.components.WizardProgressIndicator
import com.kindkrank.app.ui.theme.BackgroundBlue
import com.kindkrank.app.ui.theme.DarkGreen
import com.kindkrank.app.ui.theme.InfoBlue
import com.kindkrank.app.ui.theme.LightGreen
import com.kindkrank.app.ui.theme.MediumGreen
import com.kindkrank.app.ui.theme.PrimaryBlue
import com.kindkrank.app.ui.theme.PrimaryDark
import com.kindkrank.app.ui.theme.SuccessGreen
import com.kindkrank.app.ui.theme.SurfaceWhite
import com.kindkrank.app.ui.theme.TextPrimary
import com.kindkrank.app.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step4EmployerAndInsurance(
    isSingleParent: Boolean,
    krankenkasse: String,
    onGoHome: () -> Unit,
    onSpecialCases: () -> Unit,
    onStundenrechner: () -> Unit,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val kkData: Krankenkasse? = KRANKENKASSEN.find { it.id == krankenkasse }
    val daysPerChild = if (isSingleParent) 20 else 10
    val maxDaysTotal = if (isSingleParent) 50 else 25
    val parentLabel = if (isSingleParent) "(Alleinerziehend)" else "(je Elternteil)"

    fun openUrl(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Arzt & Krankenkasse") },
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
                .padding(horizontal = 20.dp, vertical = 8.dp),
        ) {
            WizardProgressIndicator(
                currentStep = 4,
                totalSteps = 4,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = "Schritt 4 von 4",
                fontSize = 13.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Arztbesuch & Kinderkrankengeld",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Besuchen Sie so bald wie m\u00F6glich den Kinderarzt und beantragen Sie " +
                    "anschlie\u00DFend das Kinderkrankengeld bei Ihrer Krankenkasse.",
                fontSize = 15.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(16.dp))

            // Doctor section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "\uD83E\uDE7A 1. Zum Kinderarzt",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark,
                        modifier = Modifier.padding(bottom = 10.dp),
                    )
                    DoctorStepRow(
                        step = 1,
                        title = "Kinderarzt aufsuchen",
                        text = "Bringen Sie Ihr Kind zum Kinderarzt (P\u00E4diater) oder Hausarzt. Schildern Sie die Erkrankung.",
                    )
                    Spacer(Modifier.height(10.dp))
                    DoctorStepRow(
                        step = 2,
                        title = "Kinderkrankenschein anfordern",
                        text = buildAnnotatedString {
                            append("Bitten Sie explizit um eine ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\u201E\u00E4rztliche Bescheinigung zur Vorlage bei der Krankenkasse\u201C")
                            }
                            append(" (Muster\u00A021). Diese ist ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("unbedingt notwendig")
                            }
                            append(", um Kinderkrankengeld zu beantragen.")
                        },
                    )
                    Spacer(Modifier.height(10.dp))
                    DoctorStepRow(
                        step = 3,
                        title = "Was der Arzt bescheinigt",
                        text = "Der Arzt best\u00E4tigt, dass Ihr Kind krank ist und Betreuung ben\u00F6tigt sowie die voraussichtliche Dauer.",
                    )
                    Spacer(Modifier.height(12.dp))
                    // Doctor tips
                    listOf(
                        buildAnnotatedString {
                            append("\u2022 Beantragen Sie den Kinderkrankenschein ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("gleich beim ersten Arztbesuch")
                            }
                            append(" \u2013 nachtr\u00E4glich ist es schwieriger.")
                        },
                        buildAnnotatedString {
                            append("\u2022 Digitale \u00DCbermittlung: Manche Arztpraxen k\u00F6nnen den Schein direkt an Ihre Krankenkasse senden.")
                        },
                    ).forEach { tip ->
                        Text(
                            text = tip,
                            fontSize = 13.sp,
                            color = TextSecondary,
                            lineHeight = 20.sp,
                            modifier = Modifier.padding(bottom = 4.dp),
                        )
                    }
                }
            }
            Spacer(Modifier.height(12.dp))

            // Days entitlement
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = LightGreen),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "\uD83D\uDCC5 Ihr Anspruch $parentLabel",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MediumGreen,
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = daysPerChild.toString(),
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen,
                        )
                        Text(
                            text = " Tage pro Kind pro Jahr",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MediumGreen,
                        )
                    }
                    Text(
                        text = "Maximal $maxDaysTotal Kinderkranktage gesamt (bei mehreren Kindern)",
                        fontSize = 13.sp,
                        color = SuccessGreen,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "F\u00FCr Kinder unter 12 Jahren (bzw. ohne Altersbegrenzung bei Behinderung)",
                        fontSize = 12.sp,
                        color = Color(0xFF555555),
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Spacer(Modifier.height(12.dp))

            // Krankenkasse section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val kkTitle = if (kkData != null) {
                        "\uD83C\uDFE5 2. Kinderkrankengeld beantragen bei der ${kkData.shortName}"
                    } else {
                        "\uD83C\uDFE5 2. Kinderkrankengeld beantragen"
                    }
                    Text(
                        text = kkTitle,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark,
                        modifier = Modifier.padding(bottom = 10.dp),
                    )

                    if (kkData != null) {
                        Text(
                            text = kkData.digitalSubmissionNote,
                            fontSize = 14.sp,
                            color = TextPrimary,
                            lineHeight = 21.sp,
                            modifier = Modifier.padding(bottom = 12.dp),
                        )
                    } else {
                        Text(
                            text = "Wenden Sie sich direkt an Ihre Krankenkasse und reichen Sie " +
                                "den Kinderkrankenschein (Muster 21) ein.",
                            fontSize = 14.sp,
                            color = TextPrimary,
                            lineHeight = 21.sp,
                            modifier = Modifier.padding(bottom = 12.dp),
                        )
                    }

                    listOf(
                        buildAnnotatedString { append("Kinderkrankenschein (Muster 21) einreichen") },
                        buildAnnotatedString { append("Antrag auf Kinderkrankengeld stellen") },
                        buildAnnotatedString {
                            append("Kinderkrankengeld betr\u00E4gt ca. ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("90\u00A0% des Nettolohns")
                            }
                            append(" (max. 90\u00A0% des Regelentgelts)")
                        },
                    ).forEach { item ->
                        Text(
                            text = buildAnnotatedString {
                                append("\u2705 ")
                                append(item)
                            },
                            fontSize = 14.sp,
                            color = TextPrimary,
                            lineHeight = 21.sp,
                            modifier = Modifier.padding(bottom = 6.dp),
                        )
                    }

                    // Hint for hourly-wage workers
                    Spacer(Modifier.height(10.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = InfoBlue),
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "\uD83D\uDD52 Arbeiten Sie auf Stundenbasis?",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryDark,
                                modifier = Modifier.padding(bottom = 4.dp),
                            )
                            Text(
                                text = "Bei variablem Stunden- oder Schichtlohn berechnet die Krankenkasse " +
                                    "das Kinderkrankengeld aus Ihrem Durchschnittsverdienst der letzten 3 Monate. " +
                                    "Schätzen Sie Ihren Tagessatz mit unserem Rechner:",
                                fontSize = 13.sp,
                                color = TextSecondary,
                                lineHeight = 20.sp,
                                modifier = Modifier.padding(bottom = 8.dp),
                            )
                            OutlinedButton(
                                onClick = onStundenrechner,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue),
                                border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryBlue),
                            ) {
                                Text(
                                    text = "\uD83E\uDDEE Stundenlohn-Rechner öffnen",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                        }
                    }

                    if (kkData != null) {
                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = { openUrl(kkData.onlinePortalUrl) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        ) {
                            Text(
                                text = "\uD83C\uDF10 Online-Portal ${kkData.shortName}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        OutlinedButton(
                            onClick = {
                                openUrl("tel:${kkData.hotline.replace(" ", "")}")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryBlue),
                        ) {
                            Text(
                                text = "\uD83D\uDCDE Hotline: ${kkData.hotline}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(14.dp))

            // Summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = InfoBlue),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "\uD83C\uDF89 Das Wichtigste in K\u00FCrze",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark,
                        modifier = Modifier.padding(bottom = 10.dp),
                    )
                    Text(
                        text = "1. Kind krank \u2192 Arbeitgeber sofort informieren (Tag\u00A01!)\n" +
                            "2. Arztbesuch \u2192 Kinderkrankenschein (Muster\u00A021)\n" +
                            "3. Kinderkrankengeld bei Krankenkasse beantragen\n" +
                            "4. Krankenkasse zahlt ~90\u00A0% des Nettolohns",
                        fontSize = 14.sp,
                        color = TextPrimary,
                        lineHeight = 24.sp,
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
            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = onSpecialCases,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryBlue),
            ) {
                Text(
                    text = "\u2139\uFE0F  Sonderf\u00E4lle & Ausnahmen anzeigen",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DoctorStepRow(
    step: Int,
    title: String,
    text: String,
) = DoctorStepRow(
    step = step,
    title = title,
    text = buildAnnotatedString { append(text) },
)

@Composable
private fun DoctorStepRow(
    step: Int,
    title: String,
    text: androidx.compose.ui.text.AnnotatedString,
) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(PrimaryBlue),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = step.toString(),
                color = SurfaceWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                modifier = Modifier.padding(bottom = 4.dp),
            )
            Text(
                text = text,
                fontSize = 13.sp,
                color = TextPrimary,
                lineHeight = 20.sp,
            )
        }
    }
}

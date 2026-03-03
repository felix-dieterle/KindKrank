package com.kindkrank.app.ui.screens.wizard

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                title = { Text("Arbeitgeber & Kasse") },
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
                text = "Arbeitgeber & Krankenkasse",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Jetzt müssen Sie Ihren Arbeitgeber informieren und das Kinderkrankengeld " +
                    "bei Ihrer Krankenkasse beantragen.",
                fontSize = 15.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(16.dp))

            // Employer section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "👔 1. Arbeitgeber informieren",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark,
                        modifier = Modifier.padding(bottom = 10.dp),
                    )
                    Text(
                        text = "Teilen Sie Ihrem Arbeitgeber so früh wie möglich mit, dass Sie " +
                            "aufgrund der Erkrankung Ihres Kindes zu Hause bleiben müssen.",
                        fontSize = 14.sp,
                        color = TextPrimary,
                        lineHeight = 21.sp,
                        modifier = Modifier.padding(bottom = 12.dp),
                    )
                    val employerSteps = listOf(
                        buildAnnotatedString { append("Arbeitgeber telefonisch oder per E-Mail informieren") },
                        buildAnnotatedString { append("Kinderkrankenschein beim Arbeitgeber einreichen") },
                        buildAnnotatedString {
                            append("Arbeitnehmer hat ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("keinen Anspruch auf Lohnfortzahlung")
                            }
                            append(" durch den Arbeitgeber – die Zahlung erfolgt durch die Krankenkasse")
                        },
                        buildAnnotatedString {
                            append("Der Arbeitgeber muss die Fehlzeit ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("genehmigen (unbezahlte Freistellung)")
                            }
                        },
                    )
                    employerSteps.forEach { step ->
                        Text(
                            text = buildAnnotatedString {
                                append("✅ ")
                                append(step)
                            },
                            fontSize = 14.sp,
                            color = TextPrimary,
                            lineHeight = 21.sp,
                            modifier = Modifier.padding(bottom = 6.dp),
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
                        text = "📅 Ihr Anspruch $parentLabel",
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
                        text = "Für Kinder unter 12 Jahren (bzw. ohne Altersbegrenzung bei Behinderung)",
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
                        "🏥 2. Kinderkrankengeld beantragen bei der ${kkData.shortName}"
                    } else {
                        "🏥 2. Kinderkrankengeld beantragen"
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
                            append("Kinderkrankengeld beträgt ca. ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("90 % des Nettolohns")
                            }
                            append(" (max. 90 % des Regelentgelts)")
                        },
                    ).forEach { item ->
                        Text(
                            text = buildAnnotatedString {
                                append("✅ ")
                                append(item)
                            },
                            fontSize = 14.sp,
                            color = TextPrimary,
                            lineHeight = 21.sp,
                            modifier = Modifier.padding(bottom = 6.dp),
                        )
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
                                text = "🌐 Online-Portal ${kkData.shortName}",
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
                                text = "📞 Hotline: ${kkData.hotline}",
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
                        text = "🎉 Das Wichtigste in Kürze",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark,
                        modifier = Modifier.padding(bottom = 10.dp),
                    )
                    Text(
                        text = "1. Kind krank → Arzt → Kinderkrankenschein\n" +
                            "2. Arbeitgeber informieren & Freistellung beantragen\n" +
                            "3. Kinderkrankengeld bei Krankenkasse beantragen\n" +
                            "4. Krankenkasse zahlt ~90 % des Nettolohns",
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
                    text = "↩ Zurück zur Startseite",
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
                    text = "ℹ\uFE0F  Sonderfälle & Ausnahmen anzeigen",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

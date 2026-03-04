package com.kindkrank.app.ui.screens.wizard

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kindkrank.app.ui.components.WizardProgressIndicator
import com.kindkrank.app.ui.theme.BackgroundBlue
import com.kindkrank.app.ui.theme.InfoBlue
import com.kindkrank.app.ui.theme.LightYellow
import com.kindkrank.app.ui.theme.PrimaryBlue
import com.kindkrank.app.ui.theme.PrimaryDark
import com.kindkrank.app.ui.theme.SurfaceWhite
import com.kindkrank.app.ui.theme.TextPrimary
import com.kindkrank.app.ui.theme.TextSecondary
import com.kindkrank.app.ui.theme.WarningAmber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step3Doctor(
    isSingleParent: Boolean,
    krankenkasse: String,
    onNext: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Arbeitgeber informieren") },
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
                currentStep = 3,
                totalSteps = 4,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = "Schritt 3 von 4",
                fontSize = 13.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Arbeitgeber sofort informieren",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Noch heute – auch wenn Sie noch nicht beim Arzt waren! " +
                    "Ihr Arbeitgeber muss so früh wie möglich wissen, dass Sie nicht kommen können.",
                fontSize = 15.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(16.dp))

            // Urgency card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = LightYellow),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "\u23F0", fontSize = 28.sp)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Sofortmaßnahme – Tag 1",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDark,
                            modifier = Modifier.padding(bottom = 4.dp),
                        )
                        Text(
                            text = buildAnnotatedString {
                                append("Rufen Sie Ihren Arbeitgeber ")
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = WarningAmber)) {
                                    append("noch heute")
                                }
                                append(" an oder schreiben Sie eine E-Mail – auch wenn Sie noch nicht beim Arzt waren.")
                            },
                            fontSize = 14.sp,
                            color = TextPrimary,
                            lineHeight = 21.sp,
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))

            EmployerActionCard(
                step = 1,
                title = "\uD83D\uDCF1 Arbeitgeber anrufen oder E-Mail schreiben",
                text = "Teilen Sie mit, dass Ihr Kind krank ist und Sie die Betreuung übernehmen müssen. " +
                    "Sie können heute nicht zur Arbeit kommen.",
            )
            Spacer(Modifier.height(12.dp))

            EmployerActionCard(
                step = 2,
                title = "\uD83D\uDCAC Was Sie mitteilen",
                text = buildAnnotatedString {
                    append("\u201EMein Kind ist krank und ich kann es nicht allein lassen. Ich werde heute ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("nicht zur Arbeit kommen")
                    }
                    append(" und k\u00FCmmere mich um einen Arzttermin.\u201C")
                },
            )
            Spacer(Modifier.height(12.dp))

            EmployerActionCard(
                step = 3,
                title = "\uD83D\uDCC4 Kinderkrankenschein kommt nach",
                text = "Den ärztlichen Nachweis (Muster 21) erhalten Sie beim Arztbesuch und " +
                    "können ihn dem Arbeitgeber nachreichen. Er muss Ihnen die Fehlzeit als " +
                    "unbezahlte Freistellung genehmigen.",
            )
            Spacer(Modifier.height(16.dp))

            // Tips
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = InfoBlue),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "\uD83D\uDCA1 Wichtige Hinweise",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark,
                    )
                    Spacer(Modifier.height(10.dp))
                    listOf(
                        buildAnnotatedString {
                            append("\u2022 Ihr Arbeitgeber hat ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("keinen Anspruch auf sofortigen Arztnachweis")
                            }
                            append(" \u2013 die Krankmeldung reicht f\u00FCr Tag 1.")
                        },
                        buildAnnotatedString {
                            append("\u2022 Sie haben ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("keinen Anspruch auf Lohnfortzahlung")
                            }
                            append(" durch den Arbeitgeber \u2013 das Kinderkrankengeld zahlt die Krankenkasse.")
                        },
                        buildAnnotatedString {
                            append("\u2022 Je fr\u00FCher Sie Bescheid geben, desto besser f\u00FCr die Arbeitsorganisation Ihres Arbeitgebers.")
                        },
                    ).forEach { tip ->
                        Text(
                            text = tip,
                            fontSize = 14.sp,
                            color = TextPrimary,
                            lineHeight = 21.sp,
                            modifier = Modifier.padding(bottom = 6.dp),
                        )
                    }
                }
            }
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            ) {
                Text(
                    text = "Arbeitgeber wurde informiert \u2192",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun EmployerActionCard(
    step: Int,
    title: String,
    text: String,
) = EmployerActionCard(
    step = step,
    title = title,
    text = buildAnnotatedString { append(text) },
)

@Composable
private fun EmployerActionCard(
    step: Int,
    title: String,
    text: androidx.compose.ui.text.AnnotatedString,
) {
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
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = step.toString(),
                    color = SurfaceWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.width(14.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDark,
                    modifier = Modifier.padding(bottom = 6.dp),
                )
                Text(
                    text = text,
                    fontSize = 14.sp,
                    color = TextPrimary,
                    lineHeight = 21.sp,
                )
            }
        }
    }
}

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
                title = { Text("Arztbesuch") },
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
                text = "Zum Arzt",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Der erste Schritt: Ihr Kind muss von einem Arzt untersucht werden und " +
                    "Sie benötigen eine offizielle Bescheinigung.",
                fontSize = 15.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(20.dp))

            DoctorActionCard(
                step = 1,
                title = "🩺 Kinderarzt aufsuchen",
                text = "Bringen Sie Ihr Kind zum Kinderarzt (Pädiater) oder Hausarzt. Schildern Sie die Erkrankung.",
            )
            Spacer(Modifier.height(12.dp))

            DoctorActionCard(
                step = 2,
                title = "📄 Kinderkrankenschein anfordern",
                text = buildAnnotatedString {
                    append("Bitten Sie explizit um eine ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("„ärztliche Bescheinigung zur Vorlage bei der Krankenkasse“")
                    }
                    append(" (Kinderkrankenschein / Muster 21). Diese ist ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("unbedingt notwendig")
                    }
                    append(", um Kinderkrankengeld zu beantragen.")
                },
            )
            Spacer(Modifier.height(12.dp))

            DoctorActionCard(
                step = 3,
                title = "📋 Was der Arzt bescheinigt",
                text = "Der Arzt bescheinigt, dass Ihr Kind krank ist und Betreuung benötigt sowie " +
                    "die voraussichtliche Dauer der Erkrankung. Sie können für diese Zeit zu Hause bleiben.",
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
                        text = "💡 Wichtige Tipps",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark,
                    )
                    Spacer(Modifier.height(10.dp))
                    listOf(
                        buildAnnotatedString {
                            append("• Beantragen Sie den Kinderkrankenschein ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("gleich beim ersten Arztbesuch")
                            }
                            append(" – nachträglich ist es schwieriger.")
                        },
                        buildAnnotatedString {
                            append("• Falls Ihr Kind ins Krankenhaus muss, fragen Sie dort ebenfalls " +
                                "nach einer entsprechenden Bescheinigung.")
                        },
                        buildAnnotatedString {
                            append("• Digitale Übermittlung: Manche Arztpraxen können den Schein " +
                                "direkt an Ihre Krankenkasse senden.")
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
                    text = "Ich habe den Kinderkrankenschein →",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DoctorActionCard(
    step: Int,
    title: String,
    text: String,
) = DoctorActionCard(
    step = step,
    title = title,
    text = buildAnnotatedString { append(text) },
)

@Composable
private fun DoctorActionCard(
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

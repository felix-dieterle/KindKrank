package com.kindkrank.app.ui.screens.wizard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step1FamilySituation(
    onNext: (isSingleParent: Boolean) -> Unit,
    onBack: () -> Unit,
    onGrenzgaenger: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Familiensituation") },
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            WizardProgressIndicator(currentStep = 1, totalSteps = 4)

            Text(
                text = "Schritt 1 von 4",
                fontSize = 13.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Ihre Familiensituation",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Damit wir Sie optimal begleiten können, benötigen wir einige Angaben zu Ihrer Situation.",
                fontSize = 15.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
            )
            Spacer(Modifier.height(24.dp))

            Text(
                text = "Wie ist Ihre Situation?",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(12.dp))

            SituationCard(
                emoji = "👫",
                title = "Beide Elternteile berufstätig",
                subtitle = "Sie und Ihr Partner / Ihre Partnerin sind beide erwerbstätig",
                onClick = { onNext(false) },
            )

            Spacer(Modifier.height(12.dp))

            SituationCard(
                emoji = "🧑",
                title = "Alleinerziehend",
                subtitle = "Sie erziehen Ihr Kind allein und sind berufstätig",
                onClick = { onNext(true) },
            )

            Spacer(Modifier.height(12.dp))

            SituationCard(
                emoji = "🤒",
                title = "Partner/in nicht berufstätig, aber krank",
                subtitle = "Ihr Partner / Ihre Partnerin übernimmt normalerweise die Kinderbetreuung, ist aber selbst krank und kann das Kind nicht betreuen",
                onClick = { onNext(false) },
            )

            Spacer(Modifier.height(12.dp))

            SituationCard(
                emoji = "\uD83C\uDDE8\uD83C\uDDED",
                title = "Ich arbeite in der Schweiz (Grenzg\u00E4nger)",
                subtitle = "Sie wohnen in Deutschland, sind aber bei einem Schweizer Arbeitgeber angestellt",
                onClick = onGrenzgaenger,
            )

            Spacer(Modifier.height(16.dp))

            // Info box
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = InfoBlue),
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .background(InfoBlue, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                ) {
                    Text(
                        text = "💡 Was ändert sich?",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark,
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Beide Eltern berufstätig: ")
                            }
                            append("Jedes Elternteil hat Anspruch auf ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("10 Kinderkranktage pro Kind pro Jahr")
                            }
                            append(" (max. 25 Tage gesamt bei mehreren Kindern).")
                        },
                        fontSize = 14.sp,
                        color = TextPrimary,
                        lineHeight = 21.sp,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Alleinerziehend: ")
                            }
                            append("Sie haben Anspruch auf ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("20 Kinderkranktage pro Kind pro Jahr")
                            }
                            append(" (max. 50 Tage gesamt).")
                        },
                        fontSize = 14.sp,
                        color = TextPrimary,
                        lineHeight = 21.sp,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Partner/in krank: ")
                            }
                            append("Ist der nicht berufstätige Elternteil selbst krank und kann das Kind nicht betreuen, haben Sie als berufstätiger Elternteil Anspruch auf ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("10 Kinderkranktage pro Kind pro Jahr")
                            }
                            append(" (max. 25 Tage gesamt).")
                        },
                        fontSize = 14.sp,
                        color = TextPrimary,
                        lineHeight = 21.sp,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    Text(
                        text = "Für Kinder unter 12 Jahren. Kinderkrankengeld wird von der Krankenkasse gezahlt.",
                        fontSize = 14.sp,
                        color = TextPrimary,
                        lineHeight = 21.sp,
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SituationCard(
    emoji: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = emoji, fontSize = 36.sp)
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDark,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = TextSecondary,
                    lineHeight = 20.sp,
                )
            }
        }
    }
}

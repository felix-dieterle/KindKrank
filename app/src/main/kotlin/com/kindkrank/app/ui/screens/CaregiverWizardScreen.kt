package com.kindkrank.app.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.kindkrank.app.data.CareRulesProvider
import com.kindkrank.app.data.Entitlement
import com.kindkrank.app.data.WorkCountry
import com.kindkrank.app.ui.components.CopyableInfoCard
import com.kindkrank.app.ui.components.CountryBadge
import com.kindkrank.app.ui.components.WizardProgressIndicator
import com.kindkrank.app.ui.theme.BackgroundBlue
import com.kindkrank.app.ui.theme.DarkGreen
import com.kindkrank.app.ui.theme.InfoBlue
import com.kindkrank.app.ui.theme.LightGreen
import com.kindkrank.app.ui.theme.LightYellow
import com.kindkrank.app.ui.theme.MediumGreen
import com.kindkrank.app.ui.theme.PrimaryBlue
import com.kindkrank.app.ui.theme.PrimaryDark
import com.kindkrank.app.ui.theme.SurfaceWhite
import com.kindkrank.app.ui.theme.TextPrimary
import com.kindkrank.app.ui.theme.TextSecondary

private val STEP_TITLES = listOf(
    "Situation & Anspruch",
    "Nachweise",
    "Arbeitgeber",
    "Geld & Recht",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaregiverWizardScreen(
    workCountry: String,
    onGoHome: () -> Unit,
    onBack: () -> Unit,
) {
    val country = WorkCountry.fromCode(workCountry)
    val entitlement = CareRulesProvider.forCountry(country).caregiverSick()
    var step by rememberSaveable { mutableStateOf(0) }

    BackHandler {
        if (step > 0) step-- else onBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Betreuungsperson krank") },
                navigationIcon = {
                    IconButton(onClick = { if (step > 0) step-- else onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zur\u00FCck")
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
            WizardProgressIndicator(currentStep = step + 1, totalSteps = 4, modifier = Modifier.fillMaxWidth())
            Text(
                text = "Schritt ${step + 1} von 4 \u00B7 ${STEP_TITLES[step]}",
                fontSize = 13.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))

            when (step) {
                0 -> CaregiverStepSituation(country, entitlement)
                1 -> CaregiverStepEvidence(entitlement)
                2 -> CaregiverStepEmployer(entitlement)
                else -> CaregiverStepMoney(country, entitlement)
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    if (step < 3) step++ else onGoHome()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            ) {
                Text(
                    text = if (step < 3) "Weiter" else "\u21A9 Zur\u00FCck zur Startseite",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.height(8.dp))

            if (step > 0) {
                OutlinedButton(
                    onClick = { step-- },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryBlue),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue),
                ) {
                    Text(text = "Zur\u00FCck", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CaregiverStepSituation(country: WorkCountry, entitlement: Entitlement) {
    CountryBadge(country = country)
    Spacer(Modifier.height(12.dp))
    Text(
        text = entitlement.title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = PrimaryDark,
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(Modifier.height(6.dp))
    Text(
        text = entitlement.subtitle,
        fontSize = 15.sp,
        color = TextSecondary,
        lineHeight = 22.sp,
    )
    Spacer(Modifier.height(16.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = LightGreen),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = entitlement.dayLabel,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MediumGreen,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = entitlement.dayValue,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = entitlement.paySource,
                fontSize = 13.sp,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                lineHeight = 19.sp,
            )
        }
    }
    Spacer(Modifier.height(12.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = LightYellow),
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Text(text = "\u26A0\uFE0F", fontSize = 22.sp)
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = "Sofort heute",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDark,
                    modifier = Modifier.padding(bottom = 6.dp),
                )
                Text(
                    text = entitlement.immediateAction,
                    fontSize = 14.sp,
                    color = TextPrimary,
                    lineHeight = 21.sp,
                )
            }
        }
    }
    Spacer(Modifier.height(12.dp))

    entitlement.warnings.firstOrNull()?.let { warning ->
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                Text(text = "\uD83D\uDEA8", fontSize = 18.sp)
                Spacer(Modifier.width(10.dp))
                Text(
                    text = warning,
                    fontSize = 13.sp,
                    color = TextPrimary,
                    lineHeight = 20.sp,
                )
            }
        }
    }
}

@Composable
private fun CaregiverStepEvidence(entitlement: Entitlement) {
    SectionTitle(
        title = "Diese Nachweise brauchen Sie",
        subtitle = "Besorgen Sie die Unterlagen so fr\u00FCh wie m\u00F6glich \u2013 am besten noch heute.",
    )
    Spacer(Modifier.height(16.dp))

    entitlement.documents.forEachIndexed { index, document ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = (index + 1).toString(),
                        color = SurfaceWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    text = document,
                    fontSize = 14.sp,
                    color = TextPrimary,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 3.dp),
                )
            }
        }
    }

    Spacer(Modifier.height(4.dp))
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = InfoBlue),
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
            Text(text = "\uD83D\uDCA1", fontSize = 18.sp)
            Spacer(Modifier.width(10.dp))
            Text(
                text = "Wichtig: Die \u00E4rztliche Bescheinigung wird f\u00FCr die erkrankte " +
                    "Betreuungsperson ausgestellt \u2013 nicht f\u00FCr Sie.",
                fontSize = 13.sp,
                color = TextPrimary,
                lineHeight = 20.sp,
            )
        }
    }
}

@Composable
private fun CaregiverStepEmployer(entitlement: Entitlement) {
    SectionTitle(
        title = "Arbeitgeber informieren",
        subtitle = "Informieren Sie Ihren Arbeitgeber am ersten Tag \u2013 m\u00FCndlich oder per E-Mail.",
    )
    Spacer(Modifier.height(16.dp))

    entitlement.steps.forEachIndexed { index, careStep ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = (index + 1).toString(),
                    color = SurfaceWhite,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = careStep.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDark,
                    modifier = Modifier.padding(bottom = 3.dp),
                )
                Text(
                    text = careStep.text,
                    fontSize = 13.sp,
                    color = TextPrimary,
                    lineHeight = 20.sp,
                )
            }
        }
    }

    Spacer(Modifier.height(4.dp))
    CopyableInfoCard(
        copyText = entitlement.employerTemplate,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "\uD83D\uDCE7 Textvorlage f\u00FCr Ihren Arbeitgeber",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                modifier = Modifier.padding(bottom = 8.dp, end = 20.dp),
            )
            Text(
                text = entitlement.employerTemplate,
                fontSize = 13.sp,
                color = TextPrimary,
                lineHeight = 20.sp,
                modifier = Modifier.padding(end = 20.dp),
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Tippen Sie auf die Karte, um den Text zu kopieren.",
                fontSize = 11.sp,
                color = TextSecondary,
            )
        }
    }
}

@Composable
private fun CaregiverStepMoney(country: WorkCountry, entitlement: Entitlement) {
    SectionTitle(
        title = "Geld & rechtliche Grundlage",
        subtitle = "Wer zahlt \u2013 und worauf k\u00F6nnen Sie sich berufen?",
    )
    Spacer(Modifier.height(16.dp))

    entitlement.moneySources.forEach { source ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = source.label,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDark,
                    modifier = Modifier.padding(bottom = 3.dp),
                )
                Text(
                    text = source.text,
                    fontSize = 13.sp,
                    color = TextPrimary,
                    lineHeight = 20.sp,
                )
            }
        }
    }

    Spacer(Modifier.height(8.dp))
    Text(
        text = "Rechtliche Grundlage",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = PrimaryDark,
        modifier = Modifier.padding(bottom = 8.dp),
    )
    entitlement.legalBasis.forEach { basis ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = InfoBlue),
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = basis.label,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue,
                    modifier = Modifier.padding(bottom = 2.dp),
                )
                Text(
                    text = basis.text,
                    fontSize = 13.sp,
                    color = TextPrimary,
                    lineHeight = 19.sp,
                )
            }
        }
    }

    Spacer(Modifier.height(8.dp))
    entitlement.warnings.forEach { warning ->
        Text(
            text = buildAnnotatedString {
                append("\u2022 ")
                withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append(warning) }
            },
            fontSize = 13.sp,
            color = TextPrimary,
            lineHeight = 20.sp,
            modifier = Modifier.padding(bottom = 6.dp),
        )
    }

    Spacer(Modifier.height(8.dp))
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = LightGreen),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "\uD83C\uDF89 Das Wichtigste in K\u00FCrze",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            entitlement.steps.forEachIndexed { index, careStep ->
                Text(
                    text = "${index + 1}. ${careStep.title}",
                    fontSize = 13.sp,
                    color = TextPrimary,
                    lineHeight = 21.sp,
                )
            }
        }
    }

    Spacer(Modifier.height(12.dp))
    Text(
        text = "Diese Informationen sind allgemeiner Natur und ersetzen keine Rechtsberatung. " +
            if (country == WorkCountry.SWITZERLAND) {
                "Grenzg\u00E4nger-Situationen k\u00F6nnen komplex sein \u2013 fragen Sie im Zweifel " +
                    "Ihren Arbeitgeber, Ihre Krankenkasse oder eine Rechtsberatung."
            } else {
                "Pr\u00FCfen Sie Ihren Arbeitsvertrag und wenden Sie sich im Zweifel an Ihre " +
                    "Pflegekasse oder eine Rechtsberatung."
            },
        fontSize = 11.sp,
        color = TextSecondary,
        lineHeight = 17.sp,
    )
}

@Composable
private fun SectionTitle(title: String, subtitle: String) {
    Text(
        text = title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = PrimaryDark,
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(Modifier.height(6.dp))
    Text(
        text = subtitle,
        fontSize = 15.sp,
        color = TextSecondary,
        lineHeight = 22.sp,
    )
}

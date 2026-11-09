package com.kindkrank.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kindkrank.app.data.WorkCountry
import com.kindkrank.app.ui.components.CountrySelector
import com.kindkrank.app.ui.components.FeedbackDialog
import com.kindkrank.app.ui.theme.BackgroundBlue
import com.kindkrank.app.ui.theme.PrimaryBlue
import com.kindkrank.app.ui.theme.PrimaryDark
import com.kindkrank.app.ui.theme.SurfaceWhite
import com.kindkrank.app.ui.theme.TextSecondary

@Composable
fun HomeScreen(
    onStartChildSick: (WorkCountry) -> Unit,
    onStartCaregiverSick: (WorkCountry) -> Unit,
    onSpecialCases: () -> Unit,
) {
    var showFeedbackDialog by rememberSaveable { mutableStateOf(false) }
    var selectedCode by rememberSaveable { mutableStateOf(WorkCountry.GERMANY.code) }
    val selectedCountry = WorkCountry.fromCode(selectedCode)

    if (showFeedbackDialog) {
        FeedbackDialog(onDismiss = { showFeedbackDialog = false })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBlue)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "\uD83E\uDD12", fontSize = 56.sp)
        Spacer(Modifier.height(6.dp))
        Text(
            text = "KindKrank",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryDark,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Schritt f\u00FCr Schritt durch die Kindergesundheit",
            fontSize = 15.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(20.dp))

        CountrySelector(
            selected = selectedCountry,
            onSelect = { selectedCode = it.code },
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Was ist passiert?",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = PrimaryDark,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(10.dp))

        SituationCard(
            emoji = "\uD83C\uDFE5",
            title = "Mein Kind ist krank",
            subtitle = "Kinderkrankengeld, Arztbesuch und Arbeitgeber",
            accent = PrimaryBlue,
            onClick = { onStartChildSick(selectedCountry) },
        )

        Spacer(Modifier.height(12.dp))

        SituationCard(
            emoji = "\uD83E\uDD12",
            title = "Betreuungsperson ist krank",
            subtitle = "Mutter / Partner/in f\u00E4llt aus \u2013 Sie m\u00FCssen die Kinder betreuen",
            accent = Color(0xFF1E8449),
            onClick = { onStartCaregiverSick(selectedCountry) },
        )

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            onClick = onSpecialCases,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryBlue),
        ) {
            Text(
                text = "\u2139\uFE0F  Sonderf\u00E4lle & Ausnahmen",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryBlue,
            )
        }

        Spacer(Modifier.height(4.dp))

        TextButton(
            onClick = { showFeedbackDialog = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "\uD83D\uDCA1 Verbesserung melden",
                fontSize = 14.sp,
                color = TextSecondary,
            )
        }
    }
}

@Composable
private fun SituationCard(
    emoji: String,
    title: String,
    subtitle: String,
    accent: Color,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = emoji, fontSize = 40.sp)
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = accent,
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = TextSecondary,
                    lineHeight = 19.sp,
                )
            }
            Text(text = "\u203A", fontSize = 26.sp, color = accent)
        }
    }
}

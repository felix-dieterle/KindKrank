package com.kindkrank.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kindkrank.app.ui.components.FeedbackDialog
import com.kindkrank.app.ui.theme.BackgroundBlue
import com.kindkrank.app.ui.theme.PrimaryBlue
import com.kindkrank.app.ui.theme.PrimaryDark
import com.kindkrank.app.ui.theme.SurfaceWhite
import com.kindkrank.app.ui.theme.TextSecondary

@Composable
fun HomeScreen(
    onStartWizard: () -> Unit,
    onSpecialCases: () -> Unit,
    onEntwicklungsmeilensteine: () -> Unit,
) {
    var showFeedbackDialog by remember { mutableStateOf(false) }

    if (showFeedbackDialog) {
        FeedbackDialog(onDismiss = { showFeedbackDialog = false })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBlue)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Header
        Text(text = "\uD83E\uDD12", fontSize = 70.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            text = "KindKrank",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryDark,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Schritt für Schritt durch die\nKinderkrankheit",
            fontSize = 16.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
        )

        Spacer(Modifier.height(28.dp))

        // Info box
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Ihr Kind ist krank und Sie müssen zu Hause bleiben? Diese App führt Sie durch den ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = PrimaryDark)) {
                        append("offiziellen Weg")
                    }
                    append(
                        " – von der Bescheinigung beim Arzt bis zur Beantragung des " +
                            "Kinderkrankengeldes bei Ihrer Krankenkasse."
                    )
                },
                modifier = Modifier.padding(16.dp),
                fontSize = 15.sp,
                lineHeight = 23.sp,
                textAlign = TextAlign.Center,
                color = androidx.compose.ui.graphics.Color(0xFF444444),
            )
        }

        Spacer(Modifier.height(28.dp))

        // Main CTA button
        Button(
            onClick = onStartWizard,
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "\uD83C\uDFE5", fontSize = 28.sp)
                Text(
                    text = "Mein Kind ist krank",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Jetzt Schritt für Schritt vorgehen",
                    fontSize = 13.sp,
                    color = androidx.compose.ui.graphics.Color(0xCCFFFFFF),
                )
            }
        }

        Spacer(Modifier.height(14.dp))

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
                text = "ℹ\uFE0F  Sonderfälle & Ausnahmen",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = onEntwicklungsmeilensteine,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryBlue),
        ) {
            Text(
                text = "🌱 Entwicklungsmeilensteine",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Spacer(Modifier.height(8.dp))

        TextButton(
            onClick = { showFeedbackDialog = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "💡 Verbesserung melden",
                fontSize = 14.sp,
                color = TextSecondary,
            )
        }
    }
}

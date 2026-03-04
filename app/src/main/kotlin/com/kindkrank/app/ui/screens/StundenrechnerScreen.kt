package com.kindkrank.app.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kindkrank.app.ui.theme.BackgroundBlue
import com.kindkrank.app.ui.theme.InfoBlue
import com.kindkrank.app.ui.theme.LightGreen
import com.kindkrank.app.ui.theme.LightYellow
import com.kindkrank.app.ui.theme.MediumGreen
import com.kindkrank.app.ui.theme.PrimaryBlue
import com.kindkrank.app.ui.theme.PrimaryDark
import com.kindkrank.app.ui.theme.SurfaceWhite
import com.kindkrank.app.ui.theme.TextPrimary
import com.kindkrank.app.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StundenrechnerScreen(onBack: () -> Unit) {
    var stundenlohnInput by remember { mutableStateOf("") }
    var wochenstundenInput by remember { mutableStateOf("") }

    val stundenlohn = stundenlohnInput.replace(",", ".").toDoubleOrNull()
    val wochenstunden = wochenstundenInput.replace(",", ".").toDoubleOrNull()

    val bruttoMonatlich = if (stundenlohn != null && wochenstunden != null) {
        stundenlohn * wochenstunden * (52.0 / 12.0)
    } else {
        null
    }
    val bruttoTaeglich = bruttoMonatlich?.div(30.0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stundenlohn-Rechner") },
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
                text = "🧮 Kinderkrankengeld bei Stundenlohn",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Schätzen Sie Ihren täglichen Kinderkrankengeld-Satz bei einem Arbeitsvertrag auf Stundenbasis.",
                fontSize = 14.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 21.sp,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(16.dp))

            // Explanation card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = InfoBlue),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ℹ️ So wird Kinderkrankengeld beim Stundenlohn berechnet",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("Auch bei Stunden- oder Schichtlohn haben Sie als GKV-Mitglied vollen Anspruch " +
                                "auf Kinderkrankengeld. Die Krankenkasse berechnet das ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Regelentgelt")
                            }
                            append(" auf Basis Ihres durchschnittlichen Brutto-Einkommens der letzten ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("3 Monate")
                            }
                            append(" vor Beginn der Erkrankung. Daraus ergibt sich: ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Kinderkrankengeld ≈ 90 % Ihres Netto-Tagesregelentgelts.")
                            }
                        },
                        fontSize = 13.sp,
                        color = TextPrimary,
                        lineHeight = 20.sp,
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "📄 Wichtig: Reichen Sie bei der Krankenkasse eine Bescheinigung Ihres Arbeitgebers " +
                            "über Ihren tatsächlichen Verdienst der letzten 3 Monate ein (Entgeltnachweis). " +
                            "Der genaue Auszahlungsbetrag hängt von Ihrer Steuerklasse und Ihrem individuellen " +
                            "Nettoverdienst ab.",
                        fontSize = 13.sp,
                        color = TextPrimary,
                        lineHeight = 20.sp,
                    )
                }
            }
            Spacer(Modifier.height(16.dp))

            // Calculator inputs
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Ihre Angaben",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark,
                        modifier = Modifier.padding(bottom = 12.dp),
                    )
                    OutlinedTextField(
                        value = stundenlohnInput,
                        onValueChange = { stundenlohnInput = it },
                        label = { Text("Brutto-Stundenlohn (€/h)") },
                        placeholder = { Text("z.B. 18,50") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = wochenstundenInput,
                        onValueChange = { wochenstundenInput = it },
                        label = { Text("Durchschnittliche Wochenstunden") },
                        placeholder = { Text("z.B. 32") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )
                }
            }
            Spacer(Modifier.height(16.dp))

            // Result card
            if (bruttoTaeglich != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = LightGreen),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text(
                            text = "📊 Geschätztes Ergebnis",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MediumGreen,
                            modifier = Modifier.padding(bottom = 10.dp),
                        )
                        ResultRow(
                            label = "Geschätztes Brutto-Monatsgehalt:",
                            value = "%.2f €".format(bruttoMonatlich),
                        )
                        Spacer(Modifier.height(6.dp))
                        ResultRow(
                            label = "Brutto-Tagesregelentgelt:",
                            value = "%.2f €".format(bruttoTaeglich),
                        )
                        Spacer(Modifier.height(6.dp))
                        ResultRow(
                            label = "Kinderkrankengeld/Tag (ca.):",
                            value = "%.2f € – %.2f €".format(
                                bruttoTaeglich * 0.90 * 0.60,
                                bruttoTaeglich * 0.90 * 0.75,
                            ),
                            highlight = true,
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Spanne basiert auf typischen Nettoquoten (60–75 % brutto) je nach Steuerklasse.",
                            fontSize = 12.sp,
                            color = MediumGreen,
                            lineHeight = 18.sp,
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                ) {
                    Text(
                        text = "Geben Sie Stundenlohn und Wochenstunden ein, um Ihre Schätzung zu sehen.",
                        fontSize = 14.sp,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                    )
                }
                Spacer(Modifier.height(12.dp))
            }

            // Disclaimer
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = LightYellow),
            ) {
                Row(modifier = Modifier.padding(14.dp)) {
                    Text(text = "⚠️", fontSize = 16.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Diese Berechnung ist eine grobe Schätzung. Das tatsächliche Kinderkrankengeld " +
                            "wird von Ihrer Krankenkasse individuell auf Basis Ihres Entgeltnachweises " +
                            "berechnet. Bitte wenden Sie sich bei Fragen direkt an Ihre Krankenkasse.",
                        fontSize = 13.sp,
                        color = Color(0xFF555555),
                        lineHeight = 20.sp,
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ResultRow(label: String, value: String, highlight: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = if (highlight) MediumGreen else TextSecondary,
            fontWeight = if (highlight) FontWeight.SemiBold else FontWeight.Normal,
            modifier = Modifier.weight(1f),
            lineHeight = 20.sp,
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = value,
            fontSize = 13.sp,
            color = if (highlight) MediumGreen else TextPrimary,
            fontWeight = if (highlight) FontWeight.Bold else FontWeight.SemiBold,
        )
    }
}

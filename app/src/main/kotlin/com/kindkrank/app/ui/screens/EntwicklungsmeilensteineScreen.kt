package com.kindkrank.app.ui.screens

import android.content.Context
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kindkrank.app.data.MEILENSTEINE
import com.kindkrank.app.data.Meilenstein
import com.kindkrank.app.ui.theme.BackgroundBlue
import com.kindkrank.app.ui.theme.LightGreen
import com.kindkrank.app.ui.theme.MediumGreen
import com.kindkrank.app.ui.theme.PrimaryBlue
import com.kindkrank.app.ui.theme.PrimaryDark
import com.kindkrank.app.ui.theme.SuccessGreen
import com.kindkrank.app.ui.theme.SurfaceWhite
import com.kindkrank.app.ui.theme.TextPrimary
import com.kindkrank.app.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val PREFS_NAME = "meilensteine"
private const val PREFS_KEY_PREFIX = "milestone_"
private const val DATE_PATTERN = "dd.MM.yyyy HH:mm"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntwicklungsmeilensteineScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    val checkedDates = remember {
        val map = mutableStateMapOf<String, String>()
        MEILENSTEINE.forEach { m ->
            prefs.getString("$PREFS_KEY_PREFIX${m.id}", null)?.let { map[m.id] = it }
        }
        map
    }

    val onToggle = { meilenstein: Meilenstein ->
        if (checkedDates.containsKey(meilenstein.id)) {
            checkedDates.remove(meilenstein.id)
            prefs.edit().remove("$PREFS_KEY_PREFIX${meilenstein.id}").apply()
        } else {
            val date = SimpleDateFormat(DATE_PATTERN, Locale.GERMANY).format(Date())
            checkedDates[meilenstein.id] = date
            prefs.edit().putString("$PREFS_KEY_PREFIX${meilenstein.id}", date).apply()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Entwicklungsmeilensteine") },
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
                text = "Entwicklungsmeilensteine",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Haken Sie Meilensteine ab, sobald Ihr Kind sie erreicht. " +
                    "Das Datum wird automatisch gespeichert.",
                fontSize = 15.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(20.dp))

            val grouped = MEILENSTEINE.groupBy { it.altersgruppe }
            grouped.forEach { (altersgruppe, meilensteine) ->
                Text(
                    text = altersgruppe,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDark,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                meilensteine.forEach { meilenstein ->
                    val checkedAt = checkedDates[meilenstein.id]
                    val isChecked = checkedAt != null
                    MeilensteinItem(
                        meilenstein = meilenstein,
                        isChecked = isChecked,
                        checkedAt = checkedAt,
                        onToggle = { onToggle(meilenstein) },
                    )
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun MeilensteinItem(
    meilenstein: Meilenstein,
    isChecked: Boolean,
    checkedAt: String?,
    onToggle: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable { onToggle() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isChecked) LightGreen else SurfaceWhite,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = SuccessGreen,
                    uncheckedColor = PrimaryBlue,
                ),
            )
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = meilenstein.beschreibung,
                    fontSize = 15.sp,
                    fontWeight = if (isChecked) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isChecked) MediumGreen else TextPrimary,
                )
                if (checkedAt != null) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "✓ Erreicht am $checkedAt",
                        fontSize = 12.sp,
                        color = MediumGreen,
                    )
                }
            }
        }
    }
}

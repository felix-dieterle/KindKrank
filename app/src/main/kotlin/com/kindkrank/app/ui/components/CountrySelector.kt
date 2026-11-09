package com.kindkrank.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kindkrank.app.data.WorkCountry
import com.kindkrank.app.ui.theme.PrimaryBlue
import com.kindkrank.app.ui.theme.PrimaryDark
import com.kindkrank.app.ui.theme.SurfaceWhite
import com.kindkrank.app.ui.theme.TextHint
import com.kindkrank.app.ui.theme.TextSecondary

@Composable
fun CountryBadge(
    country: WorkCountry,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(PrimaryBlue.copy(alpha = 0.12f), RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(text = country.flag, fontSize = 14.sp)
        Text(
            text = "Arbeit in ${country.label}",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = PrimaryDark,
        )
    }
}

@Composable
fun CountrySelector(
    selected: WorkCountry,
    onSelect: (WorkCountry) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Wo arbeiten Sie?",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            WorkCountry.values().forEach { country ->
                CountryOption(
                    country = country,
                    selected = country == selected,
                    onClick = { onSelect(country) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun CountryOption(
    country: WorkCountry,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) PrimaryBlue else SurfaceWhite,
        ),
        border = if (selected) null else BorderStroke(1.dp, TextHint.copy(alpha = 0.35f)),
        elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 3.dp else 1.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = country.flag, fontSize = 24.sp)
            Spacer(Modifier.height(4.dp))
            Text(
                text = country.label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (selected) SurfaceWhite else PrimaryDark,
                textAlign = TextAlign.Center,
            )
            Text(
                text = if (country == WorkCountry.SWITZERLAND) "Grenzg\u00E4nger" else "Inland",
                fontSize = 11.sp,
                color = if (selected) SurfaceWhite.copy(alpha = 0.85f) else TextSecondary,
            )
        }
    }
}

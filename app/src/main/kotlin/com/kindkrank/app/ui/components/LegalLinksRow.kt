package com.kindkrank.app.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kindkrank.app.data.LegalLink
import com.kindkrank.app.ui.theme.PrimaryBlue
import com.kindkrank.app.ui.theme.TextHint

/**
 * Zeigt zu genannten Paragraphen direkte Links auf die amtliche Online-Fassung
 * (gesetze-im-internet.de bzw. fedlex.admin.ch).
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LegalLinksRow(
    links: List<LegalLink>,
    modifier: Modifier = Modifier,
    label: String = "Gesetzestext online",
) {
    if (links.isEmpty()) return

    val context = LocalContext.current

    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.OpenInNew,
                contentDescription = null,
                tint = TextHint,
                modifier = Modifier.size(13.dp),
            )
            Spacer(Modifier.width(4.dp))
            Text(text = label, fontSize = 11.sp, color = TextHint)
        }
        Spacer(Modifier.height(6.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            links.forEach { link ->
                AssistChip(
                    onClick = {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link.url)))
                    },
                    label = { Text(text = link.label, fontSize = 12.sp) },
                    colors = AssistChipDefaults.assistChipColors(
                        labelColor = PrimaryBlue,
                    ),
                    modifier = Modifier.padding(0.dp),
                )
            }
        }
    }
}

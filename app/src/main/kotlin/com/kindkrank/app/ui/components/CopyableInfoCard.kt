package com.kindkrank.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kindkrank.app.ui.theme.InfoBlue
import com.kindkrank.app.ui.theme.TextHint
import com.kindkrank.app.ui.util.copyToClipboard

@Composable
fun CopyableInfoCard(
    copyText: String,
    modifier: Modifier = Modifier,
    containerColor: Color = InfoBlue,
    shape: Shape = RoundedCornerShape(12.dp),
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    Card(
        modifier = modifier.clickable { copyToClipboard(context, copyText) },
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = containerColor),
    ) {
        Box {
            content()
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "Kopieren",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(16.dp),
                tint = TextHint.copy(alpha = 0.6f),
            )
        }
    }
}


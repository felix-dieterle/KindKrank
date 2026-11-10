package com.kindkrank.app.ui.util

import android.content.Context
import android.content.Intent

fun shareText(context: Context, text: String, subject: String? = null) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
        if (!subject.isNullOrBlank()) {
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
    }
    context.startActivity(Intent.createChooser(intent, "Teilen \u00FCber"))
}

package com.kindkrank.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = SurfaceWhite,
    primaryContainer = InfoBlue,
    onPrimaryContainer = PrimaryDark,
    secondary = PrimaryDark,
    onSecondary = SurfaceWhite,
    background = BackgroundBlue,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    error = ErrorRed,
    onError = SurfaceWhite,
)

@Composable
fun KindKrankTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        content = content,
    )
}

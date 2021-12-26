package dev.mateusz1913.f1results.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

val LightThemeColors = lightColors(
    primary = F1ResultsColor.Primary,
    primaryVariant = F1ResultsColor.PrimaryVariant,
    secondary = F1ResultsColor.Secondary,
    background = F1ResultsColor.LightBackground,
    error = F1ResultsColor.Error,
)

val DarkThemeColors = darkColors(
    primary = F1ResultsColor.Primary,
    primaryVariant = F1ResultsColor.PrimaryVariant,
    secondary = F1ResultsColor.Secondary,
    background = F1ResultsColor.DarkBackground,
    error = F1ResultsColor.Error,
)

@Composable
fun F1ResultsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkThemeColors
    } else {
        LightThemeColors
    }
    MaterialTheme(
        colors = colors,
        content = content
    )
}
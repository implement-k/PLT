package com.cmon.pseudoLocationTracker.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

//private val DarkColorPalette = darkColors(
//    background = md_theme_light_background,
//    surface = md_theme_light_surface,
//    onSurface = md_theme_light_onSurface,
//    primary = md_theme_light_primary,
//    onPrimary = md_theme_light_onPrimary,
//    secondary = md_theme_light_secondary
//)

private val LightColorPalette = lightColors(
    background = md_theme_light_background,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    secondary = md_theme_light_secondary
)

@Composable
fun PseudoLocationTrackerTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors =
//        if (darkTheme) {
//        DarkColorPalette
//    } else {
        LightColorPalette
//    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
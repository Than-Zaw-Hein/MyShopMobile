package com.tzh.myshop.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = primaryGreen,
    primaryVariant = primaryCharcoal,
    secondary = accentAmber
)

private val LightColorPalette = lightColors(
    primary = primaryGreen,
    primaryVariant = primaryCharcoal,
    secondary = accentAmber
)

@Composable
fun MyShopTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
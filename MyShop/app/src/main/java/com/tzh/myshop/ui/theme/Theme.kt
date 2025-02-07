package com.tzh.myshop.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryCharcoal,
    onPrimary = Color.Black,
    secondary = SecondaryColor,
    onSecondary = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryCharcoal,
    onPrimary = Color.Black,
    secondary = SecondaryColor,
    onSecondary = Color.Black,
)

@Composable
fun MyShopTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
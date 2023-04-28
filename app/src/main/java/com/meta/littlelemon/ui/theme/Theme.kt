package com.meta.littlelemon.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Olive,
    primaryVariant = Yellow,
    secondary = DarkPeach,
    secondaryVariant = Peach,
    background = Color.White,
    surface = DarkGray,
    onSurface = LightGray,
    onSecondary = DarkerGray,
    onPrimary = Color.White,
    onBackground = Color.Black
)

@Composable
fun LittleLemonTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
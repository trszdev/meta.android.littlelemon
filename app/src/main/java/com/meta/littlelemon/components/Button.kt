package com.meta.littlelemon.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val borderColor = ColorUtils.blendARGB(
        MaterialTheme.colors.primaryVariant.toArgb(),
        MaterialTheme.colors.onBackground.toArgb(),
        .3f
    )
    androidx.compose.material.Button(
        enabled = enabled,
        onClick = onClick,
        border = if (enabled) BorderStroke(1.dp, Color(borderColor)) else null,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primaryVariant,
            contentColor = MaterialTheme.colors.onBackground
        ),
        shape = MaterialTheme.shapes.large,
        modifier = modifier
    ) {
        Text(text)
    }
}
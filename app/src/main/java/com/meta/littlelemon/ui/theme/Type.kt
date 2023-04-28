package com.meta.littlelemon.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.meta.littlelemon.R

private val MarkaziText = FontFamily(
    Font(R.font.markazi_text_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.markazi_text_medium, FontWeight.Medium, FontStyle.Normal)
)

private val Karla = FontFamily(
    Font(R.font.karla_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.karla_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.karla_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.karla_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
)

internal val Typography = Typography(
    defaultFontFamily = Karla,
    h1 = TextStyle(
        fontFamily = MarkaziText,
        fontWeight = FontWeight.Medium,
        fontSize = 64.sp
    ),
    h2 = TextStyle(
        fontFamily = MarkaziText,
        fontWeight = FontWeight.Normal,
        fontSize = 38.sp
    ),
    h3 = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp
    ),
    h5 = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp
    ),
    h6 = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    body1 = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    caption = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
)
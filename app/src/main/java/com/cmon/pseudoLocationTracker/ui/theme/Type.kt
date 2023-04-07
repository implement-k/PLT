package com.cmon.pseudoLocationTracker.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cmon.pseudoLocationTracker.R

val Noto = FontFamily(
    Font(R.font.notosans_regular, FontWeight.Normal),
    Font(R.font.notosans_bold, FontWeight.Bold)
)

val Typography = Typography(
    defaultFontFamily = Noto,
    h1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp
    ),

    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)
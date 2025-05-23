package com.solaisc.scribbledash.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.solaisc.scribbledash.R

// Set of Material typography styles to start with

val bagelFont = FontFamily(
    Font(R.font.bagel_fat_one, FontWeight.Normal)
)
val outfitFont = FontFamily(
    Font(R.font.outfit, FontWeight.Normal)
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = bagelFont,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = bagelFont,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = bagelFont,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = outfitFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = outfitFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = outfitFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = bagelFont,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
)
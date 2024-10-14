package com.rajit.learning_compose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.style.TextAlign
import com.rajit.learning_compose.R

private val fontFamilyKulim = FontFamily(
    listOf(
        Font(
            resId = R.font.kulim_park_regular
        ),
        Font(
            resId = R.font.kulim_park_light,
            weight = FontWeight.Light
        )
    )
)

private val fontFamilyLato = FontFamily(
    listOf(
        Font(
            resId = R.font.lato_regular
        ),
        Font(
            resId = R.font.lato_bold,
            weight = FontWeight.Bold
        )
    )
)

//val typography = Typography(
//    displayLarge = TextStyle(
//        fontFamily = fontFamilyKulim,
//        fontWeight = FontWeight.Light,
//        fontSize = 57.sp,
//        lineHeight = 64.sp,
//        letterSpacing = (-0.25).sp
//    ),
//    displayMedium = TextStyle(
//        fontFamily = fontFamilyKulim,
//        fontSize = 45.sp,
//        lineHeight = 52.sp
//    ),
//    displaySmall = TextStyle(
//        fontFamily = fontFamilyKulim,
//        fontSize = 36.sp,
//        lineHeight = 44.sp
//    ),
//    titleMedium = TextStyle(
//        fontFamily = fontFamilyLato,
//        fontWeight = FontWeight(500),
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = (0.15).sp
//    ),
//    bodySmall = TextStyle(
//        fontFamily = fontFamilyLato,
//        fontSize = 12.sp,
//        lineHeight = 16.sp,
//        letterSpacing = (0.4).sp
//    ),
//    bodyMedium = TextStyle(
//        fontFamily = fontFamilyLato,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = (0.25).sp
//    ),
//    bodyLarge = TextStyle(
//        fontFamily = fontFamilyLato,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = (0.5).sp
//    ),
//    labelMedium = TextStyle(
//        fontFamily = fontFamilyLato,
//        fontWeight = FontWeight(500),
//        fontSize = 12.sp,
//        lineHeight = 16.sp,
//        letterSpacing = (0.5).sp,
//        textAlign = TextAlign.Center
//    ),
//    labelLarge = TextStyle(
//        fontFamily = fontFamilyLato,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = (0.1).sp
//    )
//)

/**
 * We have only used the type scales that are necessary in our app omitting all others
 */
val typography = Typography(
    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    )
)
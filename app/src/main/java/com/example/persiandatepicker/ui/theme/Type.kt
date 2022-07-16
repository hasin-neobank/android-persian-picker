package com.example.persiandatepicker.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.sp
import com.example.persiandatepicker.R


val vazir = FontFamily(
    Font(R.font.vazir_light_fd, FontWeight.Light),
    Font(R.font.vazir_fd, FontWeight.Normal),
    Font(R.font.vazir_medium_fd, FontWeight.Medium),
    Font(R.font.vazir_bold_fd, FontWeight.Bold),
)

@OptIn(ExperimentalUnitApi::class)
val AppTypography = Typography(
    defaultFontFamily = vazir,
    body1 = TextStyle(
        fontFamily = vazir,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = TextWhite
    ),
    h1 = TextStyle(
        fontFamily = vazir,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = TextWhite
    ),
    h2 = TextStyle(
        fontFamily = vazir,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        color = TextWhite
    ),
    h3 = TextStyle(
        fontFamily = vazir,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = TextWhite,
    ),
    body2 = TextStyle(
        fontFamily = vazir,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = TextWhite,

        ),

    )
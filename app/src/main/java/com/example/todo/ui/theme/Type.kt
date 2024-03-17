package com.example.todo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.todo.R


val roboto = FontFamily(
    Font(R.font.roboto_light, weight = FontWeight.Light),
    Font(R.font.roboto, weight = FontWeight.Normal),
    Font(R.font.roboto_bold, weight = FontWeight.Bold),
)
val Typography = Typography(

    bodyLarge = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
    ),

    labelSmall = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    )
)
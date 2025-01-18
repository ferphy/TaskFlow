package com.example.diaryapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.diaryapp.R

val dmSansFamily = FontFamily(
    Font(R.font.dmsans_black, FontWeight.Black),
    Font(R.font.dmsans_bold, FontWeight.Bold),
    Font(R.font.dmsans_extra_bold, FontWeight.ExtraBold),
    Font(R.font.dmsans_extra_light, FontWeight.ExtraLight),
    Font(R.font.dmsans_light, FontWeight.Light),
    Font(R.font.dmsans_medium, FontWeight.Medium),
    Font(R.font.dmsans_regular, FontWeight.Normal),
    Font(R.font.dmsans_semi_bold, FontWeight.SemiBold),
    Font(R.font.dmsans_thin, FontWeight.Thin)

)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = dmSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)
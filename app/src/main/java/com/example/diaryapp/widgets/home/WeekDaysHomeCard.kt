package com.example.diaryapp.widgets.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.ui.theme.dmSansFamily

@Preview
@Composable
fun WeekDaysHomeCard(
    dayName: String = "Mon",
    dayNumber: String = "12",
    isSelected: Boolean = false,
    onClick: (Boolean) -> Unit = {}
){
    val (cardColor, textColor) = if (isSelected) {
        Pair(Color(0xFF074F60), Color(0xFFFFFFFF))
    } else {
        Pair(Color(0xFFFFFFFF), Color(0xFF074F60))
    }

    Card(
        modifier = Modifier
            .width(45.dp)
            .height(65.dp)
            .clickable {
                onClick(true)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = dayName,
                fontFamily = dmSansFamily,
                fontSize = 14.sp,
                color = textColor
            )
            Text(
                text = dayNumber,
                fontFamily = dmSansFamily,
                fontSize = 14.sp,
                color = textColor
            )



        }
    }
}
package com.example.diaryapp.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.ui.theme.dmSansFamily

@Preview
@Composable
fun ToDoCard(
    type: String = "Hobbies",
    title: String = "Redesign the Web Screen",
    date: String = "Mon, 7 March",
) {
    val (cardColor, textColor) = if (type == "Project") {
        Pair(Color(0xFFFFFFFF), Color(0xFF074F60))
    } else if (type == "Hobbies") {
        Pair(Color(0xFFFFAE47), Color(0xFFFAFAFA))
    } else {
        Pair(Color(0xFF074F60), Color(0xFFFAFAFA))
    }
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(125.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    fontSize = 12.sp,
                    fontFamily = dmSansFamily,
                    fontWeight = FontWeight.SemiBold,
                    text = type,
                    color = textColor
                )
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit Icon",
                    tint = textColor
                )
            }

            Text(
                fontSize = 20.sp,
                fontFamily = dmSansFamily,
                fontWeight = FontWeight.Bold,
                text = title,
                color = textColor
            )

            Text(
                fontSize = 12.sp,
                fontFamily = dmSansFamily,
                fontWeight = FontWeight.SemiBold,
                text = date,
                color = textColor
            )

        }

    }
}
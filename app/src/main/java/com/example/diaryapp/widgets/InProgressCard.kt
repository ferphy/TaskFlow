package com.example.diaryapp.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.ui.theme.dmSansFamily

@Preview
@Composable
fun InProgressCard(
    type: String = "Project",
    title: String = "Redesign the Web Screen",
    date: String = "Mon, 7 March",
    progress: Float = 0.58f
) {
    val (cardColor, textColor, progressColor) = if (type == "Project") {
        Triple(Color(0xFFFFFFFF), Color(0xFF074F60), Color(0xFFFFAE47))
    } else if (type == "Hobbies") {
        Triple(Color(0xFFFFAE47), Color(0xFFFAFAFA), Color(0xFF074F60))
    } else {
        Triple(Color(0xFF074F60), Color(0xFFFAFAFA), Color(0xFFFFAE47))
    }
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(140.dp),
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
                    imageVector = Icons.Outlined.MoreHoriz,
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
            // Barra de progreso personalizada
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(50))
                    .background(textColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress)
                        .clip(RoundedCornerShape(50))
                        .background(progressColor)
                )
            }

            Text(
                fontSize = 12.sp,
                fontFamily = dmSansFamily,
                fontWeight = FontWeight.SemiBold,
                text = "Till $date",
                color = textColor
            )

        }

    }
}
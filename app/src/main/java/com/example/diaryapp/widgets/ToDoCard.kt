package com.example.diaryapp.widgets

import android.content.ClipData
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.data.ToDoItem
import com.example.diaryapp.ui.theme.dmSansFamily

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoCard(
    item: ToDoItem,
    onDragStart: (String) -> Unit = {}
) {
    val (cardColor, textColor) = when (item.type) {
        "Project" -> Pair(Color(0xFFFFFFFF), Color(0xFF074F60))
        "Hobbies" -> Pair(Color(0xFFFFAE47), Color(0xFFFAFAFA))
        else -> Pair(Color(0xFF074F60), Color(0xFFFAFAFA))
    }

    val modifier = Modifier.dragAndDropSource {
        detectTapGestures(onLongPress = {
            // Genera dinámicamente los datos a transferir
            val dataToTransfer = "title=${item.title};type=${item.type};date=${item.date};progress=${item.progress}"
            Log.d("ToDoCard", "Arrastrando datos: $dataToTransfer")
            onDragStart(dataToTransfer)
            startTransfer(
                DragAndDropTransferData(
                    ClipData.newPlainText("Card Data", dataToTransfer)
                )
            )
        })
    }

    Card(
        modifier = modifier
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
                    text = item.type,
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
                text = item.title,
                color = textColor
            )

            Text(
                fontSize = 12.sp,
                fontFamily = dmSansFamily,
                fontWeight = FontWeight.SemiBold,
                text = item.date,
                color = textColor
            )

        }

    }
}
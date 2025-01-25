package com.example.diaryapp.widgets.home

import android.content.ClipDescription
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropTarget
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
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.data.ToDoItem
import com.example.diaryapp.ui.theme.dmSansFamily


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InProgressCard(
    item: ToDoItem,
    onDrop: (String) -> Unit = {}
) {

    val (cardColor, textColor, progressColor) = when (item.type) {
        "Project" -> Triple(Color(0xFFFFFFFF), Color(0xFF074F60), Color(0xFFFFAE47))
        "Hobbies" -> Triple(Color(0xFFFFAE47), Color(0xFFFAFAFA), Color(0xFF074F60))
        else -> Triple(Color(0xFF074F60), Color(0xFFFAFAFA), Color(0xFFFFAE47))
    }

    //DROP
    val callback = remember {
        object : DragAndDropTarget {
            override fun onDrop(event: DragAndDropEvent): Boolean {
                val draggedData = event.toAndroidDragEvent().clipData.getItemAt(0).text
                Log.d("InProgressCard", "Datos recibidos: ${event.toAndroidDragEvent().clipData.getItemAt(0).text}")
                onDrop(draggedData.toString()) // Llama al callback recibido
                return true
            }
        }
    }

    val modifier = Modifier.dragAndDropTarget(
        shouldStartDragAndDrop = { event ->
            Log.d("InProgressCard", "Tipo MIME: ${event.mimeTypes()}")
            event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
        }, target = callback
    )


    Card(
        modifier = modifier
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
                    text = item.type,
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
                text = item.title,
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
                        .fillMaxWidth(item.progress)
                        .clip(RoundedCornerShape(50))
                        .background(progressColor)
                )
            }

            Text(
                fontSize = 12.sp,
                fontFamily = dmSansFamily,
                fontWeight = FontWeight.SemiBold,
                text = "Till ${item.date}",
                color = textColor
            )

        }

    }
}
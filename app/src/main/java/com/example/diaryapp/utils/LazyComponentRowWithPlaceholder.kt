package com.example.diaryapp.utils

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> LazyComponentRowWithPlaceholder(
    tasks: List<T>,
    onDrop: (String) -> Unit,
    content: @Composable (T) -> Unit
) {
    LazyRow(
        modifier = Modifier.padding(vertical = 15.dp, horizontal = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        if (tasks.isEmpty()) {
            item {
                //Creación de una box para que se puedan añadir elementos a las rows sin problemas
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(125.dp)
                        .background(Color.Transparent)
                        .dragAndDropTarget(
                            shouldStartDragAndDrop = { true },
                            target = object : DragAndDropTarget {
                                override fun onDrop(event: DragAndDropEvent): Boolean {
                                    val draggedData = event.toAndroidDragEvent().clipData.getItemAt(0).text
                                    Log.d("Placeholder", "Recibido: $draggedData")
                                    onDrop(draggedData.toString())
                                    return true
                                }
                            }
                        )
                )
            }
        } else {
            // Usar claves únicas para evitar problemas de reutilización
            items(tasks, key = { task -> task.hashCode() }) { task ->
                content(task)
            }
        }
    }
}

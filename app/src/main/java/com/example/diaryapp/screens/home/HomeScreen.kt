package com.example.diaryapp.screens.home

import android.content.ClipData
import android.content.ClipDescription
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.diaryapp.data.ToDoItem
import com.example.diaryapp.model.TaskTable
import com.example.diaryapp.utils.getWeekDays
import com.example.diaryapp.widgets.home.FloatingActionButtonHome
import com.example.diaryapp.widgets.home.HomeTopAppBar
import com.example.diaryapp.widgets.home.InProgressCard
import com.example.diaryapp.widgets.home.TaskToDoTitle
import com.example.diaryapp.widgets.home.TitleOfTheSection
import com.example.diaryapp.widgets.home.ToDoCard
import com.example.diaryapp.widgets.home.WeekDaysHomeCard


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    val weekDays = getWeekDays()
    val selectedDayIndex by remember { mutableIntStateOf(-1) }

    val taskList = homeViewModel.taskList.collectAsState().value

    val toDoTasks = taskList.filter { it.state == "To do" }
    val inProgressTasks = taskList.filter { it.state == "InProgress" }

    var draggedTaskId by remember { mutableStateOf<String?>(null) }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButtonHome(
                navController = navController
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = Color(0xFFFAFAFA)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                HomeTopAppBar(modifier = Modifier.padding(horizontal = 10.dp))
                TaskToDoTitle(
                    taskNumber = toDoTasks.size,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                DaysOfTheWeekSelector(weekDays, selectedDayIndex)
                DraggableItemsLists(toDoTasks, inProgressTasks, homeViewModel)
            }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButtonHome(
                navController = navController
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = Color(0xFFFAFAFA)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                HomeTopAppBar(modifier = Modifier.padding(horizontal = 10.dp))
                TaskToDoTitle(
                    taskNumber = toDoTasks.size,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                DaysOfTheWeekSelector(weekDays, selectedDayIndex)
                DraggableItemsLists(toDoTasks, inProgressTasks, homeViewModel)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DraggableItemsLists(
    toDoTasks: List<TaskTable>,
    inProgressTasks: List<TaskTable>,
    homeViewModel: HomeViewModel
) {
    // Estado temporal para la tarea arrastrada
    var draggedTaskId by remember { mutableStateOf<String?>(null) }

    // Section "To Do"
    TitleOfTheSection(
        title = "To do",
        taskNumber = toDoTasks.size,
        modifier = Modifier.padding(horizontal = 10.dp)
    )
    LazyComponentRowWithPlaceholder(
        toDoTasks,
        placeholderContent = {
            Text(text = "No tasks to do yet")
        }
    ) { item ->
        ToDoCard(
            item = item,
            modifier = Modifier.dragAndDropSource {
                detectTapGestures(onLongPress = {
                    draggedTaskId = item.id.toString()
                    startTransfer(
                        DragAndDropTransferData(
                            ClipData.newPlainText("Task Data", "id=${item.id}")
                        )
                    )
                    Log.d("DragSource", "Arrastrando tarea con ID: ${item.id}")
                })
            }
        )
    }
    // Section "In Progress"
    TitleOfTheSection(
        title = "In progress",
        taskNumber = inProgressTasks.size,
        modifier = Modifier.padding(horizontal = 10.dp),
    )
    LazyComponentRowWithPlaceholder(
        tasks = inProgressTasks,
        placeholderContent = {
            Text(text = "No tasks in progress yet")
        }
    ) { item ->
        InProgressCard(
            item = item,
            modifier = Modifier.dragAndDropTarget(
                shouldStartDragAndDrop = { event ->
                    Log.d("DragTarget", "Tipos MIME recibidos: ${event.mimeTypes()}")
                    event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                },
                target = object : DragAndDropTarget {
                    override fun onDrop(event: DragAndDropEvent): Boolean {
                        val draggedData = event.toAndroidDragEvent().clipData.getItemAt(0).text.toString()
                        Log.d("DragTarget", "Datos recibidos: $draggedData")
                        val taskId = draggedData.split("=").lastOrNull()
                        if (taskId != null) {
                            homeViewModel.handleDrop(taskId, "InProgress")
                            Log.d("DragTarget", "Tarea con ID: $taskId movida a InProgress")
                            return true
                        }
                        return false
                    }
                }
            )
        )
    }
    // Section "Completed"
    TitleOfTheSection(
        title = "Completed!",
        taskNumber = 0,
        modifier = Modifier.padding(horizontal = 10.dp),
    )
    LazyComponentRowWithPlaceholder(
        emptyList<TaskTable>(),
        placeholderContent = {
            Text(text = "No tasks completed yet")
        }
    ) { item ->
        ToDoCard(
            item = item
        )
    }
}

@Composable
private fun DaysOfTheWeekSelector(
    weekDays: List<Pair<String, String>>,
    selectedDayIndex: Int
) {
    var selectedDayIndex1 = selectedDayIndex
    LazyRow(
        modifier = Modifier.padding(vertical = 15.dp, horizontal = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(weekDays.size) { index ->
            val (dayName, dayNumber) = weekDays[index]
            WeekDaysHomeCard(
                dayName = dayName,
                dayNumber = dayNumber,
                isSelected = index == selectedDayIndex1, // Cambia solo si es el seleccionado
            ) {
                selectedDayIndex1 = index // Actualiza el índice seleccionado
                Log.d("HomeScreen", "Día seleccionado: $dayName")
            }
        }
    }
}

@Composable
fun <T> LazyComponentRowWithPlaceholder(
    tasks: List<T>,
    placeholderContent: @Composable () -> Unit,
    taskContent: @Composable (T) -> Unit
) {
    if (tasks.isEmpty()) {
        // Mostrar el placeholder si la lista está vacía
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            placeholderContent()
        }
    } else {
        // Mostrar los elementos de la lista
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks, key = { it.hashCode() }) { task ->
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(125.dp)
                ) {
                    taskContent(task)
                }
            }
        }
    }
}




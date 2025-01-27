package com.example.diaryapp.screens.home
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.diaryapp.data.ToDoItem
import com.example.diaryapp.data.getDummyData
import com.example.diaryapp.utils.LazyComponentRowWithPlaceholder
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

    // Listas mutables para "To Do" y "In Progress"
    val toDoTasks = remember { mutableStateListOf(*getDummyData().toTypedArray()) }
    val inProgressTasks = remember { mutableStateListOf<ToDoItem>() }

    //ESTOS SON LOS VERDADEROS ELEMENTOS QUE TENGO QUE MOSTAR
    val taskList = homeViewModel.taskList.collectAsState().value



    val onDragStart: (String) -> Unit = { data ->
        Log.d("HomeScreen", "Arrastrando: $data")
    }

    val onDrop: (String) -> Unit = handleTaskDrop(toDoTasks, inProgressTasks)

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
                TaskToDoTitle(taskNumber = toDoTasks.size, modifier = Modifier.padding(horizontal = 10.dp))
                DaysOfTheWeekSelector(weekDays, selectedDayIndex)
                DraggableItemsLists(toDoTasks, onDrop, onDragStart, inProgressTasks)
            }
        }
    }
}

@Composable
private fun DraggableItemsLists(
    toDoTasks: SnapshotStateList<ToDoItem>,
    onDrop: (String) -> Unit,
    onDragStart: (String) -> Unit,
    inProgressTasks: SnapshotStateList<ToDoItem>
) {
    // Section "To Do"
    TitleOfTheSection(
        title = "To do",
        taskNumber = toDoTasks.size,
        modifier = Modifier.padding(horizontal = 10.dp)
    )
    LazyComponentRowWithPlaceholder(
        toDoTasks,
        onDrop = onDrop
    ) { item ->
        ToDoCard(
            item = item,
            onDragStart = onDragStart
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
        onDrop = onDrop
    ) { item ->
        InProgressCard(
            item = item,
            onDrop = onDrop
        )
    }
    // Section "Completed"
    TitleOfTheSection(
        title = "Completed!",
        taskNumber = 0,
        modifier = Modifier.padding(horizontal = 10.dp),
    )
    LazyComponentRowWithPlaceholder(
        emptyList<ToDoItem>(),
        onDrop = onDrop
    ) { item ->
        ToDoCard(
            item = item,
            onDragStart = onDragStart
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
private fun handleTaskDrop(
    toDoTasks: SnapshotStateList<ToDoItem>,
    inProgressTasks: SnapshotStateList<ToDoItem>
): (String) -> Unit {
    val onDrop: (String) -> Unit = { data ->
        val properties = data.split(";").associate {
            val (key, value) = it.split("=")
            key to value
        }

        val taskTitle = properties["title"]
        val task = toDoTasks.find { it.title == taskTitle }
        if (task != null) {
            toDoTasks.remove(task)
            inProgressTasks.add(task)

            Log.d("HomeScreen", "Estado actual de toDoTasks: ${
                toDoTasks.joinToString(separator = "\n") { task_items ->
                    "Title: ${task_items.title}, Type: ${task_items.type}, Date: ${task_items.date}, Progress: ${task_items.progress}"
                }
            }")
        } else {
            Log.d("HomeScreen", "No se encontró la tarea: $taskTitle")
        }
    }
    return onDrop
}






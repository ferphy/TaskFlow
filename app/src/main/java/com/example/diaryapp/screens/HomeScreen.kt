package com.example.diaryapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.ui.theme.dmSansFamily
import com.example.diaryapp.widgets.FloatingActionButtonHome
import com.example.diaryapp.widgets.HomeTopAppBar
import com.example.diaryapp.widgets.InProgressCard
import com.example.diaryapp.widgets.TitleOfTheSection
import com.example.diaryapp.widgets.ToDoCard
import com.example.diaryapp.widgets.WeekDaysHomeCard
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Preview
@Composable
fun HomeScreen() {
    val taskNumber by remember { mutableIntStateOf(5) }
    val weekDays = getWeekDays()
    var selectedDayIndex by remember { mutableIntStateOf(-1) } // Controlar el día seleccionado
    val dummyData = getDummyData()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButtonHome()
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
                Spacer(modifier = Modifier.padding(10.dp))
                TaskToDoTitle(taskNumber = taskNumber,modifier = Modifier.padding(horizontal = 10.dp))
                Spacer(modifier = Modifier.padding(10.dp))
                LazyRow(
                    modifier = Modifier.padding(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(weekDays.size) { index ->
                        val (dayName, dayNumber) = weekDays[index]
                        Box(Modifier
                            .height(80.dp)
                            .width(55.dp)) {
                            WeekDaysHomeCard(
                                dayName = dayName,
                                dayNumber = dayNumber,
                                isSelected = index == selectedDayIndex, // Cambia solo si es el seleccionado
                            ) {
                                selectedDayIndex = index // Actualiza el índice seleccionado
                                Log.d("HomeScreen", "Día seleccionado: $dayName")
                            }
                        }
                    }
                }
                TitleOfTheSection(
                    title = "To do",
                    taskNumber = 7,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                LazyRow(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(dummyData) { item ->
                        ToDoCard(
                            type = item.type,
                            title = item.title,
                            date = item.date
                        )
                    }
                }

                TitleOfTheSection(
                    title = "In progress",
                    taskNumber = 5,
                    modifier = Modifier.padding(horizontal = 10.dp),
                )
                LazyRow(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(dummyData) { item ->
                        InProgressCard(
                            type = item.type,
                            title = item.title,
                            date = item.date,
                            progress = item.progress
                        )
                    }
                }
                TitleOfTheSection(
                    title = "Completed!",
                    taskNumber = 7,
                    modifier = Modifier.padding(horizontal = 10.dp),
                )
                LazyRow(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(dummyData) { item ->
                        ToDoCard(
                            type = item.type,
                            title = item.title,
                            date = item.date
                        )
                    }
                }

            }
        }
    }
}

data class ToDoItem(
    val type: String,
    val title: String,
    val date: String,
    val progress: Float
)

fun getDummyData(): List<ToDoItem> {
    return listOf(
        ToDoItem(type = "Hobbies", title = "Redesign the Web Screen", date = "Mon, 7 March", progress = 0.58f),
        ToDoItem(type = "Project", title = "Complete the App Backend", date = "Tue, 8 March", progress = 0.70f),
        ToDoItem(type = "Hobbies", title = "Learn a New Song", date = "Wed, 9 March", progress = 0.35f),
        ToDoItem(type = "Chores", title = "Grocery Shopping", date = "Thu, 10 March", progress = 0.27f),
        ToDoItem(type = "Project", title = "Deploy the Website", date = "Fri, 11 March", progress = 0.32f),
        ToDoItem(type = "Hobbies", title = "Sketch a Portrait", date = "Sat, 12 March", progress = 0.12f),
        ToDoItem(type = "Chores", title = "Clean the Garage", date = "Sun, 13 March", progress = 0.5f)
    )
}

fun getWeekDays(): List<Pair<String, String>> {
    val calendar = Calendar.getInstance()
    val dateFormatDayName = SimpleDateFormat("EEE", Locale.getDefault()) // Ej: "Mon"
    val dateFormatDayNumber = SimpleDateFormat("d", Locale.getDefault()) // Ej: "12"

    // Aseguramos que comience desde el lunes
    calendar.firstDayOfWeek = Calendar.MONDAY
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

    // Generamos la lista de días de la semana
    return (0..6).map {
        val dayName = dateFormatDayName.format(calendar.time)
        val dayNumber = dateFormatDayNumber.format(calendar.time)
        calendar.add(Calendar.DAY_OF_YEAR, 1) // Pasar al siguiente día
        Pair(dayName, dayNumber)
    }
}

@Composable
fun TaskToDoTitle(
    taskNumber: Int,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        fontSize = 24.sp,
        fontFamily = dmSansFamily,
        fontWeight = FontWeight.Bold,
        text = buildAnnotatedString {
            append("You’ve got ")
            withStyle(style = SpanStyle(color = Color(0xFFFFAE47))) { // Cambia el color aquí
                append("$taskNumber")
            }
            append(" task today")
        },
        color = Color(0xFF074F60) // Este color será para el texto que no está en `taskNumber`
    )
}



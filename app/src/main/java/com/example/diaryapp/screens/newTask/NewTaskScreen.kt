package com.example.diaryapp.screens.newTask

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.diaryapp.model.TaskTable
import com.example.diaryapp.ui.theme.dmSansFamily
import com.example.diaryapp.widgets.GenericConfigurationRow
import com.example.diaryapp.widgets.GenericTopAppBar
import com.example.diaryapp.widgets.home.TextFieldHint
import com.example.diaryapp.widgets.newTask.DatePickerDialogWidget


@Composable
fun NewTaskScreen(
    navController: NavController,
    newTaskViewModel: NewTaskViewModel
) {

    val titleField = remember { mutableStateOf("") }
    val descriptionField = remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    var selectedReminder by remember { mutableStateOf("") }
    val subTaskList = remember { mutableStateListOf<String>() }
    var newSubTask by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    val task = TaskTable(
        title = titleField.value,
        type = selectedType,
        date = selectedDate,
        progress = 0f,
        description = descriptionField.value,
        state = "To do",
        specificTasksToDo = subTaskList,
        specificTasksDone = emptyList(),
        isCompleted = false,
    )


    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(), color = Color(0xFFFAFAFA)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                // Sección del título
                item {
                    GenericTopAppBar(
                        title = "New Task",
                        leftIcon = Icons.AutoMirrored.Filled.ArrowBack,
                        rightIcon = Icons.Default.Save,
                        onLeftIconClick = {navController.popBackStack()},
                        onRightIconClick = {
                            Log.d("NewTaskScreen", "Title: ${titleField.value}" +
                                    "Description: ${descriptionField.value}" +
                                    "showDatePicker: $showDatePicker" +
                                    "selectedDate: $selectedDate" +
                                    "selectedType: $selectedType" +
                                    "selectedReminder: $selectedReminder" +
                                    "subTaskList: $subTaskList")

                            newTaskViewModel.addTask(task)
                            navController.popBackStack()
                        }

                    )

                    TextFieldHint(
                        hint = "Title",
                        onEnterPressed = {
                            titleField.value = it
                        },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                }

                // Configuración de la fecha
                item {
                    GenericConfigurationRow(
                        title = "Due Date",
                        modifier = Modifier.padding(horizontal = 5.dp),
                        leftItem = {
                            if (selectedDate.isEmpty()) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarToday,
                                    contentDescription = "Left Icon",
                                    tint = Color(0xFF021D23),
                                )
                            } else {
                                Text(
                                    text = selectedDate,
                                    fontFamily = dmSansFamily,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                        },
                        onLeftIconClick = {
                            showDatePicker = true
                        }
                    )
                    if (showDatePicker) {
                        DatePickerDialogWidget(
                            onDismissRequest = { showDatePicker = false },
                            selectedDate = {
                                selectedDate = it
                                showDatePicker = false
                            }
                        )
                    }
                }

                // Sección de listas y recordatorios
                item {
                    GenericConfigurationRow(
                        title = "Add to list",
                        modifier = Modifier.padding(horizontal = 5.dp),
                        leftItem = {
                            GenericDropDownMenu(
                                options = listOf("Personal", "Work", "Other"),
                                selectedOption = selectedType.ifEmpty { "Personal" },
                                onOptionSelected = { selectedType = it }
                            )
                        },
                        onLeftIconClick = {}
                    )
                    GenericConfigurationRow(
                        title = "Reminder",
                        modifier = Modifier.padding(horizontal = 5.dp),
                        leftItem = {
                            GenericDropDownMenu(
                                options = listOf("None", "Yes"),
                                selectedOption = selectedReminder.ifEmpty { "None" },
                                onOptionSelected = { selectedReminder = it }
                            )
                        },
                        onLeftIconClick = {}
                    )

                    TextFieldHint(
                        hint = "Description",
                        onEnterPressed = {
                            descriptionField.value = it
                        },
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        height = 150.dp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = "Subtasks",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Color(0xFF055062),
                    )
                }

                // Subtasks list
                items(subTaskList) { subTask ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = false,
                            onCheckedChange = {} // Puedes agregar lógica aquí
                        )
                        Text(
                            text = subTask,
                            modifier = Modifier.padding(start = 8.dp),
                            fontSize = 16.sp,
                            color = Color(0xFF055062)
                        )
                    }
                }

                // Campo para añadir subtareas
                item {
                    val focusRequester = remember { FocusRequester() }
                    val keyboardController = LocalSoftwareKeyboardController.current

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = false,
                            onCheckedChange = {}
                        )
                        TextField(
                            value = newSubTask,
                            onValueChange = { newSubTask = it },
                            placeholder = {
                                Text(
                                    text = "+ add a new one",
                                    color = Color(0xFF9E9E9E)
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                cursorColor = Color(0xFF055062),
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (newSubTask.isNotEmpty()) {
                                        subTaskList.add(newSubTask)
                                        newSubTask = ""
                                        focusRequester.requestFocus()
                                    }
                                }
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                                .focusRequester(focusRequester)
                        )
                    }

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                }
            }

            // Scroll automático al final al agregar una nueva subtarea
            LaunchedEffect(subTaskList.size) {
                if (subTaskList.isNotEmpty()) {
                    listState.animateScrollToItem(subTaskList.size)
                }
            }
        }
    }
}






@Composable
fun GenericDropDownMenu(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier) {
        Text(
            text = selectedOption,
            modifier = Modifier
                .clickable { expanded = true },
            fontFamily = dmSansFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF021D23)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = {
                        Text(text = option)
                    }
                )
            }
        }
    }
}
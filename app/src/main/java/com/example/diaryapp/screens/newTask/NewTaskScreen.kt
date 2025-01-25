package com.example.diaryapp.screens.newTask

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryapp.ui.theme.dmSansFamily
import com.example.diaryapp.widgets.GenericConfigurationRow
import com.example.diaryapp.widgets.GenericTopAppBar
import com.example.diaryapp.widgets.home.TextFieldHint
import com.example.diaryapp.widgets.newTask.DatePickerDialogWidget
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.*

import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.input.ImeAction


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskScreen() {

    val firstField = remember { mutableStateOf("") }
    val firstFocusRequester = remember { FocusRequester() }
    val secondFocusRequester = remember { FocusRequester() }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    var selectedReminder by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(), color = Color(0xFFFAFAFA)
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                GenericTopAppBar(
                    title = "New Task",
                    leftIcon = Icons.AutoMirrored.Filled.ArrowBack,
                    rightIcon = Icons.Default.Save,
                )

                TextFieldHint(
                    hint = "Title",
                    onEnterPressed = {
                        firstField.value = it
                        secondFocusRequester.requestFocus()
                    },
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .focusRequester(firstFocusRequester)
                )

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
                        firstField.value = it
                        secondFocusRequester.requestFocus()
                    },
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .focusRequester(firstFocusRequester),
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

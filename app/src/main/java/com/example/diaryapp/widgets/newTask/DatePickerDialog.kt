package com.example.diaryapp.widgets.newTask

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogWidget(
    onDismissRequest: () -> Unit,
    selectedDate: (String) -> Unit
){
    val datePickerState = rememberDatePickerState()
    androidx.compose.material3.DatePickerDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            // Botón de confirmación en el DatePicker
            Button(
                onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis
                    if (selectedMillis != null) {
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val formattedDate = formatter.format(Date(selectedMillis))
                        selectedDate(formattedDate)
                    }
                    onDismissRequest()
                },
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFAE47)
                )
            ) {
                Text("Confirm")
            }
        },
        content = {
            DatePicker(
                state = datePickerState
            )
        }
    )

}


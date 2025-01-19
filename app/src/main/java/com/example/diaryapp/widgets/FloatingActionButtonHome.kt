package com.example.diaryapp.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.diaryapp.navigation.NewTaskScreenRoute

@Preview
@Composable
fun FloatingActionButtonHome(navController: NavController) {
    FloatingActionButton(
        onClick = {
            navController.navigate(NewTaskScreenRoute)
        },
        shape = CircleShape, // Forma circular
        containerColor = Color(0xFFFFAE47), // Naranja
        contentColor = Color.White // Color del ícono
    ) {
        Icon(
            imageVector = Icons.Default.Add, // Ícono de "más"
            contentDescription = "Add Task",
            modifier = Modifier.size(24.dp) // Tamaño del ícono
        )
    }
}
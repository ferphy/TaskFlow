package com.example.diaryapp.navigation

import com.example.diaryapp.data.ToDoItem
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Serializable
data object HomeScreen{
}

@Serializable
data class DetailScreen(
    val id: Int
)


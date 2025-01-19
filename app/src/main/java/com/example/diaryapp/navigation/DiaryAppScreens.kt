package com.example.diaryapp.navigation

import com.example.diaryapp.data.ToDoItem
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Serializable
data object HomeScreenRoute{
}

@Serializable
data class DetailScreenRoute(
    val id: Int
)

@Serializable
data object NewTaskScreenRoute{
}


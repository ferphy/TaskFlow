package com.example.diaryapp.data

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

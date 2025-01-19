package com.example.diaryapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID
import javax.annotation.Nonnull


@Entity(tableName = "task_tbl")
data class TaskTable (

    @Nonnull
    @PrimaryKey
    @ColumnInfo(name = "task_id")
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "progress")
    val progress: Float,

    @ColumnInfo(name = "description")
    val description: Float,

    @ColumnInfo(name = "state")
    val state: String,

    @ColumnInfo(name = "specific_tasks_to_do")
    val specificTasksToDo: List<String>,

    @ColumnInfo(name = "specific_tasks_done")
    val specificTasksDone: List<String>,

    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean,

)
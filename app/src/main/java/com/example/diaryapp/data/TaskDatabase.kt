package com.example.diaryapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diaryapp.model.Converters
import com.example.diaryapp.model.TaskDao
import com.example.diaryapp.model.TaskTable

@Database(entities = [TaskTable::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TaskDatabase: RoomDatabase(){

    abstract fun taskDao(): TaskDao
}
package com.example.diaryapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * from task_tbl")
    fun getAllTasks(): Flow<List<TaskTable>>

    @Query("SELECT * from task_tbl where task_id =:id")
    fun getTaskById(id: String): Flow<TaskTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskTable)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(task: TaskTable)

    @Query("DELETE from task_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteTask(task: TaskTable)

}
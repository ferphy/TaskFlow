package com.example.diaryapp.repository

import com.example.diaryapp.model.TaskDao
import com.example.diaryapp.model.TaskTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    suspend fun insertTask(task: TaskTable) = taskDao.insert(task)
    suspend fun updateTask(task: TaskTable) = taskDao.update(task)
    suspend fun deleteTask(task: TaskTable) = taskDao.deleteTask(task)
    suspend fun deleteTaskById(id: String) = taskDao.deleteTaskById(id)
    suspend fun deleteAllTasks() = taskDao.deleteAll()
    fun getTaskById(id: String): Flow<TaskTable> = taskDao.getTaskById(id)
    fun getAllTasks(): Flow<List<TaskTable>> = taskDao.getAllTasks().flowOn(Dispatchers.IO).conflate()
    suspend fun changeTaskState(taskId: String, newState: String) = taskDao.updateTaskState(taskId, newState)

}
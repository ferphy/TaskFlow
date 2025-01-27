package com.example.diaryapp.screens.newTask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapp.model.TaskTable
import com.example.diaryapp.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    private val _taskList = MutableStateFlow<List<TaskTable>>(emptyList())

    val taskList: StateFlow<List<TaskTable>> = _taskList

    init{
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getAllTasks().distinctUntilChanged()
                .collect { listOfTasks ->
                    if (listOfTasks.isEmpty()) {
                        _taskList.value = emptyList()
                        Log.d("taskList", ": Empty list")
                    } else {
                        _taskList.value = listOfTasks
                        Log.d("taskList", ": ${taskList.value}")
                    }
                }
        }
    }

    fun addTask(task: TaskTable) = viewModelScope.launch { taskRepository.insertTask(task) }
    fun updateTask(task: TaskTable) = viewModelScope.launch { taskRepository.updateTask(task) }
    fun removeTask(task: TaskTable) = viewModelScope.launch { taskRepository.deleteTask(task) }
    fun removeAllTasks() = viewModelScope.launch { taskRepository.deleteAllTasks()}
    fun getTaskById(id: String) = viewModelScope.launch { taskRepository.getTaskById(id) }

}
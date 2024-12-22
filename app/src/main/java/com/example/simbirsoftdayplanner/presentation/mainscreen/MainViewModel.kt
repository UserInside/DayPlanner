package com.example.simbirsoftdayplanner.presentation.mainscreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    var state by mutableStateOf(MainScreenState())

    init {
        viewModelScope.launch {
            state = state.copy(tasksList = getTasksListByDate(Date(System.currentTimeMillis())))
        }
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.onDateSelectedEvent -> {
                viewModelScope.launch {
                    state = state.copy(tasksList = getTasksListByDate(Date(event.date)))
                }
            }

            is MainScreenEvent.TaskClickedEvent -> state = state.copy() //

            is MainScreenEvent.DeleteTaskEvent -> {
                viewModelScope.launch {
                    repository.deleteTask(state.chosenTaskId)
                }
            }
        }
    }

    suspend fun getTasksListByDate(date: Date): List<Task> {
        return repository.getTaskListByDate(date)
    }
}
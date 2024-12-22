package com.example.simbirsoftdayplanner.presentation.mainscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskInteractor: TaskInteractor,
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
                    taskInteractor.deleteTask(state.chosenTaskId)
                }
            }
        }
    }

    private suspend fun getTasksListByDate(date: Date): List<Task> {
        return taskInteractor.getTaskListByDate(date)
    }
}
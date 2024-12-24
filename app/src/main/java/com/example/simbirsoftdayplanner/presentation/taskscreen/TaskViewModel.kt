package com.example.simbirsoftdayplanner.presentation.taskscreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskInteractor
import com.example.simbirsoftdayplanner.presentation.ContentState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atDate

@HiltViewModel(assistedFactory = TaskViewModelFactory::class)
class TaskViewModel @AssistedInject constructor(
    private val taskInteractor: TaskInteractor,
    @Assisted("p1") val taskId: Int,
    @Assisted("p2") val selectedDate: Int,
    @Assisted("p3") val selectedHour: Int,
) : ViewModel() {

    var state by mutableStateOf(TaskScreenState())

    init {
        fetchData()
    }

    private fun fetchData() {
        if (state.contentState == ContentState.Loading) return

        state = state.copy(contentState = ContentState.Loading)

        if (taskId != 0) {
            viewModelScope.launch {
                delay(2000) //просто чтобы посмотреть лоадер
                val task = getTaskById(taskId)
                Log.i("TSCR", "task - $task")
                state = state.copy(
                    name = task.name,
                    description = task.description,
                    startTime = task.startTime,
                    contentState = ContentState.Done,
                )
            }
        } else {
            state = state.copy(
                startTime = LocalTime(selectedHour, 0),
                contentState = ContentState.Done)
        }
    }

    fun onEvent(event: TaskScreenEvent) {
        when (event) {
            is TaskScreenEvent.AddTaskEvent -> {
                val task = Task(
                    name = state.name,
                    description = state.description,
                    startTime = state.startTime.atDate(LocalDate.fromEpochDays(selectedDate)), //написать номальную дату
                )
                viewModelScope.launch {
                    taskInteractor.addTask(task)
                }
            }

            is TaskScreenEvent.EditTaskEvent -> viewModelScope.launch {
                taskInteractor.editTask(Task())
            }

            is TaskScreenEvent.OnNameUpdatedEvent ->
                state = state.copy(name = event.text)

            is TaskScreenEvent.OnDescriptionUpdatedEvent -> state =
                state.copy(description = event.text)

            is TaskScreenEvent.OnStartTimeUpdatedEvent -> state =
                state.copy(startTime = LocalTime(event.hours, event.minutes))
        }
    }

    private suspend fun getTaskById(taskId: Int): TaskScreenState {
        val task = taskInteractor.getTaskById(taskId) ?: Task()
        return TaskScreenState(
            name = task.name,
            description = task.description,
            startTime = task.startTime.time, //исправить время. может убрать на часы
        )
    }
}

@AssistedFactory
interface TaskViewModelFactory {
    fun create(
        @Assisted("p1") taskId: Int,
        @Assisted("p2") selectedDate: Int,
        @Assisted("p3") selectedHourDate: Int,
    ): TaskViewModel
}

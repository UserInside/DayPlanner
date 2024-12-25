package com.example.simbirsoftdayplanner.presentation.mainscreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskInteractor
import com.example.simbirsoftdayplanner.presentation.ContentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskInteractor: TaskInteractor,
) : ViewModel() {

    var state by mutableStateOf(MainScreenState())

    init {
        fetchData()
    }

    private fun fetchData() {
        if (state.contentState == ContentState.Loading) return

        state = state.copy(contentState = ContentState.Loading)
        viewModelScope.launch {
            delay(1000) //просто чтобы посмотреть лоадер
            val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            state = state.copy(
                tasksList = getTasksListByDate(today),
                selectedDate = today.toEpochDays(),
                contentState = ContentState.Done,
            )
        }
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnDateSelectedEvent -> { onDateSelectedEvent(event) }
            is MainScreenEvent.OnTaskLineSelectedEvent -> { onTaskLineSelectedEvent(event) }
            is MainScreenEvent.OnEmptyLineSelectedEvent -> { onEmptyLineSelectedEvent(event) }
            is MainScreenEvent.DeleteTaskEvent -> { deleteTaskEvent() }
        }
    }

     private fun onEmptyLineSelectedEvent(event: MainScreenEvent.OnEmptyLineSelectedEvent) {
        val bottomBarState =
            if (state.bottomBarState == BottomBarState.EmptyLineSelectedState
                && state.selectedHour == event.selectedHour)
                BottomBarState.NoLineSelectedState else BottomBarState.EmptyLineSelectedState

        val newTaskList = selectLine(event.selectedHour)
        state = state.copy(
            tasksList = newTaskList,
            selectedTaskId = 0,
            selectedHour = event.selectedHour,
            bottomBarState = bottomBarState
        )
    }

    private fun onTaskLineSelectedEvent(event: MainScreenEvent.OnTaskLineSelectedEvent) {
        val bottomBarState =
            if (state.bottomBarState == BottomBarState.TaskLineSelectedState
                && event.taskId == state.selectedTaskId)
                BottomBarState.NoLineSelectedState else BottomBarState.TaskLineSelectedState

        val newTaskList = selectLine(event.startTime.hour)
        state = state.copy(
            tasksList = newTaskList,
            selectedTaskId = event.taskId,
            bottomBarState = bottomBarState
        )
    }

    private fun onDateSelectedEvent(event: MainScreenEvent.OnDateSelectedEvent) {
        val instant = Instant.fromEpochMilliseconds(event.date)
        val date: LocalDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
        viewModelScope.launch {
            state = state.copy(
                tasksList = getTasksListByDate(date),
                selectedDate = date.toEpochDays(),
            )
        }
    }

    private fun deleteTaskEvent() {
        viewModelScope.launch {
            taskInteractor.deleteTaskById(state.selectedTaskId)
            state =
                state.copy(
                    tasksList = getTasksListByDate(LocalDate.fromEpochDays(state.selectedDate))
                    , bottomBarState = BottomBarState.NoLineSelectedState
                )
        }
    }

    private suspend fun getTasksListByDate(date: LocalDate): List<TaskModel> {
        val taskList = taskInteractor.getTaskListByDate(date)
        val hoursList = taskList.map { it.startTime.hour }

        val resultList = mutableListOf<TaskModel>()
        for (i in 0..23) {
            if (i !in hoursList) {
                resultList.add(TaskModel(startTime = LocalTime(i, 0)))
            } else {
                val task: Task = taskList.find { it.startTime.hour == i } ?: Task()
                val taskModel = TaskModel(
                    id = task.id,
                    name = task.name,
                    description = task.description,
                    startTime = task.startTime.time,
                )
                resultList.add(taskModel)
            }
        }
        return resultList
    }

    private fun selectLine(selectedLineHour: Int): List<TaskModel> {
        return state.tasksList.map { task ->
            if (selectedLineHour == task.startTime.hour) {
                task.copy(isSelected = !task.isSelected)
            } else {
                task.copy(isSelected = false)
            }
        }
    }
}
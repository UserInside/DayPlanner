package com.example.simbirsoftdayplanner.presentation.mainscreen

import com.example.simbirsoftdayplanner.presentation.ContentState
import kotlinx.datetime.LocalTime

sealed class MainScreenEvent{
    object DeleteTaskEvent: MainScreenEvent()
    class OnTaskLineSelectedEvent(val taskId: Int, val startTime: LocalTime): MainScreenEvent()
    class OnEmptyLineSelectedEvent(val selectedHour: Int): MainScreenEvent()
    class OnDateSelectedEvent(val date: Long): MainScreenEvent()
}

data class MainScreenState(
    val tasksList: List<TaskModel> = listOf(),
    val selectedTaskId: Int = 0,
    val selectedDate: Int = 0,
    val selectedHour: Int = 0,
    val contentState: ContentState = ContentState.Idle,
    val bottomBarState: BottomBarState = BottomBarState.NoLineSelectedState
)

enum class BottomBarState {
    TaskLineSelectedState, EmptyLineSelectedState, NoLineSelectedState
}

data class TaskModel(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val startTime: LocalTime = LocalTime(0, 0),
    var isSelected: Boolean = false,
)
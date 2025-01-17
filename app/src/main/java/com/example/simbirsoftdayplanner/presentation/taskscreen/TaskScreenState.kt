package com.example.simbirsoftdayplanner.presentation.taskscreen

import com.example.simbirsoftdayplanner.presentation.ContentState
import kotlinx.datetime.LocalTime

sealed class TaskScreenEvent(){
    object AddTaskEvent: TaskScreenEvent()
    class OnNameUpdatedEvent(val text: String): TaskScreenEvent()
    class OnDescriptionUpdatedEvent(val text: String): TaskScreenEvent()
    class OnStartTimeUpdatedEvent(val hours: Int, val minutes: Int): TaskScreenEvent()
}

data class TaskScreenState(
    val name: String = "",
    val description: String = "",
    val startTime: LocalTime = LocalTime(0, 0),
    val contentState: ContentState = ContentState.Idle
)
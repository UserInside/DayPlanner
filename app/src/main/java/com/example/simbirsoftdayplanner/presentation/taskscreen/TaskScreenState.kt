package com.example.simbirsoftdayplanner.presentation.taskscreen

import com.example.simbirsoftdayplanner.domain.Task
import java.sql.Time
import java.sql.Timestamp


sealed class TaskScreenEvent(){
    class AddTaskEvent(): TaskScreenEvent()
    class EditTaskEvent(task: Task): TaskScreenEvent()
    class onNameChanged(val text: String): TaskScreenEvent()
    class onDescriptionChanged(val text: String): TaskScreenEvent()
}

data class TaskScreenState(
    var task: Task,
//    val name: String = "",
//    val description: String = "",
//    val startTime: Timestamp ,
//    val finishTime: Timestamp ,
)
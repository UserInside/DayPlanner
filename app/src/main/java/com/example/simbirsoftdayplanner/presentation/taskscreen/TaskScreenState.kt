package com.example.simbirsoftdayplanner.presentation.taskscreen

import com.example.simbirsoftdayplanner.domain.Task
import java.sql.Timestamp


sealed class TaskScreenEvent(){
    class AddTaskEvent(): TaskScreenEvent()
    class EditTaskEvent(task: Task): TaskScreenEvent()
    class onNameChangedEvent(val text: String): TaskScreenEvent()
    class onDescriptionChangedEvent(val text: String): TaskScreenEvent()
}

data class TaskScreenState(
    val name: String = "",
    val description: String = "",
    val startTime: Timestamp = Timestamp(0),
    val finishTime: Timestamp = Timestamp(0),
)  {  companion object {

//    fun mock() = TaskScreenState(
////        id = 17,
//        name = "Mock Task",
//        description = "Mock task description",
//        startTime = Timestamp(147600000),
//        finishTime = Timestamp(147610000),
//    )
//
//    fun emptyMock() = TaskScreenState(
////        id = 19,
//        name = "",
//        description = "",
//        startTime = Timestamp(0),
//        finishTime = Timestamp(0),
//    )
}}
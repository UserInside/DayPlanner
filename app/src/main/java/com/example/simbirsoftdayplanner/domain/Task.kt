package com.example.simbirsoftdayplanner.domain

import java.sql.Timestamp

data class Task(
    val id: Int = 0, //todo исправить
    val name: String = "",
    val description: String = "",
    val startTime: Timestamp = Timestamp(0),
    val finishTime: Timestamp = Timestamp(0),
) {
    companion object {

        fun mock() = Task(
            id = 17,
            name = "Mock Task",
            description = "Mock task description",
            startTime = Timestamp(147600000),
            finishTime = Timestamp(147610000),
        )

        fun emptyMock() = Task(
            id = 19,
            name = "",
            description = "",
            startTime = Timestamp(0),
            finishTime = Timestamp(0),
        )
    }
}

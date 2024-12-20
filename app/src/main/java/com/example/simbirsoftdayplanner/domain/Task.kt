package com.example.simbirsoftdayplanner.domain

import java.sql.Timestamp

data class Task(
    val id: Int,
    val name: String,
    val description: String,
    val startTime: Timestamp,
    val finishTime: Timestamp,
) {
    companion object {

        fun mock() = Task(
            id = 17,
            name = "Mock Task",
            description = "Mock task description",
            startTime = Timestamp(147600000),
            finishTime = Timestamp(147610000),
        )
    }
}

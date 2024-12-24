package com.example.simbirsoftdayplanner.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class Task(
    val id: Int = 0, //todo исправить
    val name: String = "",
    val description: String = "",
    val startTime: LocalDateTime = LocalDateTime(0, 1, 1, 0, 0),
)

package com.example.simbirsoftdayplanner.domain

import kotlinx.datetime.LocalDateTime

data class Task(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val startTime: LocalDateTime = LocalDateTime(0, 1, 1, 0, 0),
)

package com.example.simbirsoftdayplanner.common

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object Converter {

    fun convertLongToLocalDateTime(long: Long): LocalDateTime {
        val instant = Instant.fromEpochMilliseconds(long)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return localDateTime
    }

    fun convertLocalDateTimeToLong(localDateTime: LocalDateTime): Long {
        return localDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }
}
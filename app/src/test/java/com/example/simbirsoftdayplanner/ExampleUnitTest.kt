package com.example.simbirsoftdayplanner

import com.example.simbirsoftdayplanner.common.Converter
import com.example.simbirsoftdayplanner.data.db.TaskEntity
import com.example.simbirsoftdayplanner.data.mapDataToDomain
import com.example.simbirsoftdayplanner.data.mapDomainToData
import com.example.simbirsoftdayplanner.domain.Task
import junit.framework.TestCase.assertEquals
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.Test

class UnitTests{

    @Test
    fun testMapper() {
        val task = Task(
            id = 0,
            name = "Task",
            description = "TaskDescription",
            startTime = LocalDateTime(0, 1, 1, 0, 0)
        )

        val taskEntity = TaskEntity(
            id = 0,
            name = "Task",
            description = "TaskDescription",
            taskStartTime = Converter.convertLocalDateTimeToLong(LocalDateTime(0, 1, 1, 0, 0))
        )

        assertEquals(taskEntity, task.mapDomainToData())
        assertEquals(task, taskEntity.mapDataToDomain())
    }

    @Test
    fun testConverters() {
        val localDateTime = LocalDateTime(2024,12,25,17,15)
        val long = 1735136100000L

        assertEquals(long, Converter.convertLocalDateTimeToLong(localDateTime))
        assertEquals(localDateTime, Converter.convertLongToLocalDateTime(long))
    }
}


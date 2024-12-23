package com.example.simbirsoftdayplanner.data

import com.example.simbirsoftdayplanner.common.Converter.convertLocalDateTimeToLong
import com.example.simbirsoftdayplanner.common.Converter.convertLongToLocalDateTime
import com.example.simbirsoftdayplanner.data.db.TaskEntity
import com.example.simbirsoftdayplanner.domain.Task
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DataDomainMapper {

    fun mapDataToDomain(taskEntity: TaskEntity): Task {
        return Task(
            id = taskEntity.id,
            name = taskEntity.name ?: "",
            description = taskEntity.description ?: "",
            startTime = convertLongToLocalDateTime(taskEntity.dateStart!!),   //todo сделать нормально
            finishTime = convertLongToLocalDateTime(taskEntity.dateFinish!!),
        )
    }

    fun mapDomainToData(task: Task): TaskEntity {
        return TaskEntity(
            name = task.name,
            description = task.description,
            dateStart = convertLocalDateTimeToLong(task.startTime),
            dateFinish = convertLocalDateTimeToLong(task.finishTime),
        )
    }
}
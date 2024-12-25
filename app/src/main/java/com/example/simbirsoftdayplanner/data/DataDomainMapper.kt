package com.example.simbirsoftdayplanner.data

import com.example.simbirsoftdayplanner.common.Converter.convertLocalDateTimeToLong
import com.example.simbirsoftdayplanner.common.Converter.convertLongToLocalDateTime
import com.example.simbirsoftdayplanner.data.db.TaskEntity
import com.example.simbirsoftdayplanner.domain.Task

fun TaskEntity.mapDataToDomain(): Task {
    return Task(
        id = this.id,
        name = this.name ?: "",
        description = this.description ?: "",
        startTime = convertLongToLocalDateTime(this.taskStartTime ?: 0) ,
    )
}

fun Task.mapDomainToData(): TaskEntity {
    return TaskEntity(
        name = this.name,
        description = this.description,
        taskStartTime = convertLocalDateTimeToLong(this.startTime),
    )
}

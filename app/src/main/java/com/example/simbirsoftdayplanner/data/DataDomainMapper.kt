package com.example.simbirsoftdayplanner.data

import com.example.simbirsoftdayplanner.data.db.TaskEntity
import com.example.simbirsoftdayplanner.domain.Task

object DataDomainMapper {

    fun mapDataToDomain(taskEntity: TaskEntity): Task {
        return Task(
            id = taskEntity.id,
            name = taskEntity.name!!,
            description = taskEntity.description!!,
            startTime = taskEntity.date_start!!,
            finishTime = taskEntity.date_finish!!,
        )
    }

    fun mapDomainToData(task: Task): TaskEntity {
        return TaskEntity(
            name = task.name,
            description = task.description,
            date_start = task.startTime,
            date_finish = task.finishTime,
        )
    }
}
package com.example.simbirsoftdayplanner.data

import com.example.simbirsoftdayplanner.data.db.TaskEntity
import com.example.simbirsoftdayplanner.domain.Task
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object DataDomainMapper {
//    val simpleDateTime = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
//    val simpleDate = SimpleDateFormat("dd/MM/yyyy")
////    val currentDateTime = simpleDate.format(java.util.Date())
//    val date = simpleDate.format(java.sql.Date())

    fun mapDataToDomain(taskEntity: TaskEntity): Task {


        return Task(
            id = taskEntity.id,
            name = taskEntity.name!!,
            description = taskEntity.description!!,
            startTime = Timestamp(taskEntity.date_start!!), //todo сделать нормально
            finishTime = Timestamp(taskEntity.date_finish!!),
        )
    }

    fun mapDomainToData(task: Task): TaskEntity {
        return TaskEntity(
            name = task.name,
            description = task.description,
            date_start = task.startTime.time,
            date_finish = task.finishTime.time,
        )
    }
}
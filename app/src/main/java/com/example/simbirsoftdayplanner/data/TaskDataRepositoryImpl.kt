package com.example.simbirsoftdayplanner.data

import android.util.Log
import com.example.simbirsoftdayplanner.common.Converter
import com.example.simbirsoftdayplanner.data.db.TaskDao
import com.example.simbirsoftdayplanner.data.db.TaskEntity
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.minutes

class TaskDataRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    fun formatDateToLong(date: LocalDate): Long {
        return date.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds() // todo ыерно ли начлао дня ??
    }

    // todo тестировать форматеры . правлиьно ли форматирует

    override suspend fun getTaskListByDate(date: LocalDate): List<Task> {

        val dateStart = formatDateToLong(date)
        val dateFinish = formatDateToLong(date).plus(86400000)
        val x = taskDao.getTasksListByDate(dateStart, dateFinish)
        val res = x.map { DataDomainMapper.mapDataToDomain(it) }
//        Log.i("TDRI", "input date - $date, dateStart - $dateStart, dateFinish - $dateFinish")
//        Log.i("TDRI", "dalee taskEntityList - $x")
//        Log.i("TDRI", "result taskList - $res")
        return res
    }

    override suspend fun getTaskById(taskId: Int): Task? =
        taskDao.getTaskById(taskId)?.let { DataDomainMapper.mapDataToDomain(it) }

    override suspend fun addTask(task: Task) {
        val taskEntity = DataDomainMapper.mapDomainToData(task)

//        Log.i("TDRI", "input add task - $task")
//        Log.i("TDRI", "dalee taskEntity - $taskEntity")
        taskDao.addTask(taskEntity)

//        taskDao.addTask(TaskEntity.mock1())
//        taskDao.addTask(TaskEntity.mock2())
//        taskDao.addTask(TaskEntity.mock3())

    }

    override suspend fun editTask(task: Task) {
        taskDao.editTask(DataDomainMapper.mapDomainToData(task))
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

}


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
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class TaskDataRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    fun formatTimestampToDate(timestamp: Long): String {
        val dateTime =
            LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return dateTime.format(formatter)
    }

    fun formatDateToLong(date: LocalDate): Long {
        return date.atStartOfDayIn(TimeZone.UTC).epochSeconds // Получаем количество секунд с эпохи Unix (1 января 1970 года)
    }

    // todo тестировать форматеры . правлиьно ли форматирует

    override suspend fun getTaskListByDate(date: LocalDate): List<Task> {
        val dateTime = formatDateToLong(date)
        val x = taskDao.getTasksListByDate(date)
        val res = x.map { DataDomainMapper.mapDataToDomain(it) }
        Log.i("TDRI", "input date - $date")
        Log.i("TDRI", "dalee datetime - $dateTime")
        Log.i("TDRI", "dalee x - $x")
        Log.i("TDRI", "list res - $res")
        return res
    }

    override suspend fun getTaskById(taskId: Int): Task? =
        taskDao.getTaskById(taskId)?.let { DataDomainMapper.mapDataToDomain(it) }

    override suspend fun addTask(task: Task) {
//        val taskEntity = DataDomainMapper.mapDomainToData(task)
//        taskDao.addTask(taskEntity)
//        Log.i("TDRI", "input add task - $task")
//        Log.i("TDRI", "dalee taskEntity - $taskEntity")
        taskDao.addTask(TaskEntity.mock1())
        taskDao.addTask(TaskEntity.mock2())
        taskDao.addTask(TaskEntity.mock3())

    }

    override suspend fun editTask(task: Task) {
        taskDao.editTask(DataDomainMapper.mapDomainToData(task))
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

}


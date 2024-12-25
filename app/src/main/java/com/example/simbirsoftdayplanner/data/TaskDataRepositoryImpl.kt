package com.example.simbirsoftdayplanner.data

import com.example.simbirsoftdayplanner.data.db.TaskDao
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

class TaskDataRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    override suspend fun getTaskListByDate(date: LocalDate): List<Task> {

        val dateStart = formatDateToLong(date)
        val dateFinish = formatDateToLong(date).plus(86400000)
        val x = taskDao.getTasksListByDate(dateStart, dateFinish)
        val res = x.map { it.mapDataToDomain() }
        return res
    }

    override suspend fun getTaskById(taskId: Int): Task? =
        taskDao.getTaskById(taskId)?.mapDataToDomain()

    override suspend fun addTask(task: Task) {
        val taskEntity = task.mapDomainToData()
        taskDao.addTask(taskEntity)
    }

    override suspend fun editTask(task: Task) {
        taskDao.editTask(task.mapDomainToData())
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTaskById(taskId)
    }

    private fun formatDateToLong(date: LocalDate): Long {
        return date.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }
}


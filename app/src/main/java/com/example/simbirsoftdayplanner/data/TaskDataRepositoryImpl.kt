package com.example.simbirsoftdayplanner.data

import com.example.simbirsoftdayplanner.data.db.TaskDao
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskRepository
import java.util.Date

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    override suspend fun getTaskListByDate(date: Date): List<Task> =
        taskDao.getTasksListByDate(date.time).map { DataDomainMapper.mapDataToDomain(it) }


    override suspend fun getTaskById(taskId: Int): Task {
        val task = DataDomainMapper.mapDataToDomain(taskDao.getTaskById(taskId))
        return task
    }

    override suspend fun addTask(task: Task) {
        val taskEntity = DataDomainMapper.mapDomainToData(task)
        taskDao.addTask(taskEntity)
    }

    override suspend fun editTask(task: Task) {
        taskDao.editTask(DataDomainMapper.mapDomainToData(task))
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

}


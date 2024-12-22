package com.example.simbirsoftdayplanner.data

import android.util.Log
import com.example.simbirsoftdayplanner.data.db.TaskDao
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskRepository
import java.util.Date

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    override suspend fun getTaskListByDate(date: Date): List<Task> =
        taskDao.getTasksListByDate(date.time).map { DataDomainMapper.mapDataToDomain(it) }


    override suspend fun getTaskById(taskId: Int): Task {
        val task =
            DataDomainMapper.mapDataToDomain(taskDao.getTaskById(taskId))  //todo сделать нормально/  NPE . вроде починил
        return task
    }

    override suspend fun addTask(task: Task) {
        val taskEntity = DataDomainMapper.mapDomainToData(task)
        taskDao.addTask(taskEntity)
        Log.i("TaskDataRepImpl", "addtask proceeded to db")
    }

    override suspend fun editTask(task: Task) {
        taskDao.editTask(DataDomainMapper.mapDomainToData(task))
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

}


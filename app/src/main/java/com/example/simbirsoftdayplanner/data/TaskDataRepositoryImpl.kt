package com.example.simbirsoftdayplanner.data

import android.util.Log
import com.example.simbirsoftdayplanner.data.db.TaskDao
import com.example.simbirsoftdayplanner.domain.Task
import com.example.simbirsoftdayplanner.domain.TaskRepository

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    override suspend fun getTaskList(): List<Task> {
        val listToMap = taskDao.getTasksList().value
        val tasksList = mutableListOf<Task>()
        for (taskEntity in listToMap!!) {  //todo delete assertion
            tasksList.add(DataDomainMapper.mapDataToDomain(taskEntity))
        }
        return tasksList
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        val task = taskDao.getTaskById(taskId).value?.let { DataDomainMapper.mapDataToDomain(it) } //todo сделать нормально/  NPE
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
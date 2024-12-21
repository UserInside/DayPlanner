package com.example.simbirsoftdayplanner.data

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

    override suspend fun getTaskById(taskId: Int): Task {
        val task = Task.mock()
//            DataDomainMapper.mapDataToDomain(taskDao.getTaskById(taskId).value!!) //todo сделать нормально/  NPE

        return task
    }

    override fun addTask(task: Task) {
        val taskEntity = DataDomainMapper.mapDomainToData(task)
        taskDao.addTask(taskEntity)
    }

    override fun editTask(task: Task) {
        taskDao.editTask(DataDomainMapper.mapDomainToData(task))
    }

    override fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

}
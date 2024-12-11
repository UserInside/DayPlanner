package com.example.simbirsoftdayplanner.data
//
//import androidx.lifecycle.LiveData
//import com.example.simbirsoftdayplanner.data.db.TaskEntity
//
//interface TaskRepository {
//
//    fun getTasksListStream(): LiveData<List<TaskEntity>>
//
//    fun getTaskStream(taskId: Int): LiveData<TaskEntity?>
//
//    suspend fun addTask(task: TaskEntity)
//
//    suspend fun editTask(task: TaskEntity)
//
//    suspend fun deleteTask(task: TaskEntity)
//
//}
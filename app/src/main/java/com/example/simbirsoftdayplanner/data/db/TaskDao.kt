package com.example.simbirsoftdayplanner.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE taskStartTime BETWEEN :dateStart AND :dateFinish")
    suspend fun getTasksListByDate(dateStart: Long, dateFinish: Long): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE taskId = :taskId")
    suspend fun getTaskById(taskId: Int): TaskEntity?

    @Insert(entity = TaskEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE taskId = :taskId")
    suspend fun deleteTaskById(taskId: Int)
}
package com.example.simbirsoftdayplanner.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.sql.Date

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE DATETIME(dateStart) = :date")
    suspend fun getTasksListByDate(date: Long): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE taskId = :taskId")
    suspend fun getTaskById(taskId: Int): TaskEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TaskEntity)

    @Update
    suspend fun editTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE taskId = :taskId") //@Delete
    suspend  fun deleteTask(taskId: Int)
}
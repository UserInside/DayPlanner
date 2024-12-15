package com.example.simbirsoftdayplanner.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getTasksList(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE taskId = :taskId")
    fun getTask(taskId: Int): LiveData<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(task: TaskEntity)

    @Update
    fun editTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE taskId = :taskId") //@Delete
    fun deleteTask(taskId: Int)
}
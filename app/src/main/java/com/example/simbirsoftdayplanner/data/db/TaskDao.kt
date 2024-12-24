package com.example.simbirsoftdayplanner.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.datetime.LocalDate

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE taskStartTime BETWEEN :dateStart AND :dateFinish")
    suspend fun getTasksListByDate(dateStart: Long, dateFinish: Long): List<TaskEntity> //todo Timestamp?

    @Query("SELECT * FROM tasks WHERE taskId = :taskId")
    suspend fun getTaskById(taskId: Int): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TaskEntity)

    @Update
    suspend fun editTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE taskId = :taskId") //@Delete
    suspend fun deleteTask(taskId: Int)
}


// date() возвращает дату в формате “yyyy-mm-dd”
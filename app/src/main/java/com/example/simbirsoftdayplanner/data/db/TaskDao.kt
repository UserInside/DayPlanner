package com.example.simbirsoftdayplanner.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.datetime.LocalDate

@Dao
interface TaskDao {

    //    @Query("SELECT * FROM tasks WHERE DATE(dateStart) = :date")
//    @Query("SELECT * FROM tasks WHERE datetime(dateStart, 'unixepoch') = :date")
    //@Query("SELECT * FROM events WHERE event_date BETWEEN '2023-04-01' AND '2023-04-30'")
    @Query("SELECT * FROM tasks WHERE strftime('%Y-%m-%d', datetime(dateStart)) = :date")
    suspend fun getTasksListByDate(date: Long): List<TaskEntity>

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
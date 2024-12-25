package com.example.simbirsoftdayplanner.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TasksRoomDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}
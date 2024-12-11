package com.example.simbirsoftdayplanner.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

//@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
//abstract class TasksRoomDatabase : RoomDatabase() {
//
//    abstract fun taskDao(): TaskDao
//
//    companion object {
//
//        @Volatile
//        private var INSTANCE: TasksRoomDatabase? = null
//
//        @OptIn(InternalCoroutinesApi::class)
//        fun getDatabase(context: Context): TasksRoomDatabase {
//            return INSTANCE ?: synchronized(this) {
//                Room.databaseBuilder(context, TasksRoomDatabase::class.java, "tasks_database")
//                    .fallbackToDestructiveMigration()
//                    .build()
//                    .also{ INSTANCE = it}
//            }
//        }
//    }
//}
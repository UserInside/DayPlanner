package com.example.simbirsoftdayplanner.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    val id: Int = 0

    @ColumnInfo(name = "taskName")
    val name: String? = null

    @ColumnInfo(name = "taskTime")
    val time: Double? = null

    @ColumnInfo(name = "taskDescription")
    val description: String? = null
}
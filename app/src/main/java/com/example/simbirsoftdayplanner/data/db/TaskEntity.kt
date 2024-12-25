package com.example.simbirsoftdayplanner.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tasks", indices = [Index("taskId")])
data class TaskEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    var id: Int = 0,

    @ColumnInfo(name = "taskName")
    val name: String? = null,

    @ColumnInfo(name = "taskDescription")
    val description: String? = null,

    @ColumnInfo(name = "taskStartTime")
    val taskStartTime: Long? = null,

)
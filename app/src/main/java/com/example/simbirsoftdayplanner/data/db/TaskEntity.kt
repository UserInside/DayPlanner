package com.example.simbirsoftdayplanner.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
class TaskEntity(

    @ColumnInfo(name = "taskName")
    val name: String? = null,

    @ColumnInfo(name = "taskDescription")
    val description: String? = null,

    @ColumnInfo(name = "dateStart")
    val date_start: Long? = null,

    @ColumnInfo(name = "dateFinish")
    val date_finish: Long? = null,
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    var id: Int = 0
}
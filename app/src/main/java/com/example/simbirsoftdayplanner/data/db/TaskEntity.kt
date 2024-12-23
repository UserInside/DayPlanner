package com.example.simbirsoftdayplanner.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
class TaskEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    var id: Int = 1,

    @ColumnInfo(name = "taskName")
    val name: String? = null,

    @ColumnInfo(name = "taskDescription")
    val description: String? = null,

    @ColumnInfo(name = "dateStart")
    val dateStart: Long? = null,

    @ColumnInfo(name = "dateFinish")
    val dateFinish: Long? = null,
) {
    override fun toString(): String {
        return "id = $id, name = $name, desc = $description, stTime = $dateStart, fnTime = $dateFinish"
    }
}
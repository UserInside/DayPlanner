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

    companion object {

        fun mock1(): TaskEntity{
            return TaskEntity(
                id = 7,
                name = "box1",
                description = "dscr2",
                dateStart = 1734940800000,
                dateFinish = 1734944400000,
            )
        }

        fun mock2(): TaskEntity{
            return TaskEntity(
                id = 9,
                name = "box2",
                description = "dscr2",
                dateStart = 1734966000000,
                dateFinish = 1734969600000,
            )
        }

        fun mock3(): TaskEntity{
            return TaskEntity(
                id = 13,
                name = "box3",
                description = "dscr3",
                dateStart = 1734980400000,
                dateFinish = 1734984000000,
            )
        }
    }
}
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

    @ColumnInfo(name = "taskStartTime")
    val taskStartTime: Long? = null,

) {
    override fun toString(): String {
        return "id = $id, name = $name, desc = $description, tas = $taskStartTime"
    }

    companion object {

        fun mock1(): TaskEntity{
            return TaskEntity(
                id = 7,
                name = "box1",
                description = "dscr2",
                taskStartTime = 1734940800000,
            )
        }

        fun mock2(): TaskEntity{
            return TaskEntity(
                id = 9,
                name = "box2",
                description = "dscr2",
                taskStartTime = 1734966000000,
            )
        }

        fun mock3(): TaskEntity{
            return TaskEntity(
                id = 13,
                name = "box3",
                description = "dscr3",
                taskStartTime = 1734980400000,
            )
        }
    }
}
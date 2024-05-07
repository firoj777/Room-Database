package com.example.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="student_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "frist_name")
    val fristname: String,
    @ColumnInfo(name = "last_name")
    val lastname: String,
    @ColumnInfo(name = "roll_no")
    val rollno:Int,
)

package com.example.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudentDao {
    @Insert(onConflict =OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("SELECT * FROM student_table ORDER BY id ASC")
    fun getAll():List<Student>

    @Query("SELECT * FROM student_table WHERE roll_no= :roll")
    suspend fun findById(roll: Int): Student

    @Query("DELETE FROM student_table")
    suspend fun deleteAll()

    @Query("SELECT(SELECT COUNT(*)FROM student_table)=0")
    fun isEmpty(): Boolean

}
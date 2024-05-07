package com.example.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase:RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object{
        @Volatile
        private var INSTANCE:StudentDatabase? = null

        fun getDatabase(context:Context):StudentDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "student_database"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }
}
package com.example.roomdatabase

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings.Global
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roomdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var studentDatabase: StudentDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        studentDatabase= StudentDatabase.getDatabase(this)

        binding.saveBtn.setOnClickListener {
            saveData()
        }
        binding.searchBtn.setOnClickListener {
            searchData()
        }

        binding.deleteBtn.setOnClickListener {
            GlobalScope.launch {
                studentDatabase.studentDao().deleteAll()
            }
        }


    }

    private fun searchData() {
        val rollNo = binding.rollnoEt.text.toString()
        if (rollNo.isNotEmpty()){
            lateinit var student: Student
            GlobalScope.launch {
                student = studentDatabase.studentDao().findById(rollNo.toInt())
                if (studentDatabase.studentDao().isEmpty()) {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(this@MainActivity,"No data found",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    displayData(student)
                }
            }
        }
    }

    private suspend fun displayData(student: Student) {
        withContext(Dispatchers.Main){
            binding.fristnameEt.setText(student.fristname.toString())
            binding.lastnameEt.setText(student.lastname.toString())
            binding.rollnoEt.setText(student.rollno.toString())

        }

    }

    private fun saveData() {
      val fristName = binding.fristnameEt.text.toString()
      val lastName = binding.lastnameEt.text.toString()
      val rollno = binding.rollnoEt.text.toString()

        if (fristName.isNotEmpty()&& lastName.isNotEmpty()&& rollno.isNotEmpty()){
            val student = Student(0,fristName,lastName,rollno.toInt())
            GlobalScope.launch {
                studentDatabase.studentDao().insert(student)
            }
            binding.fristnameEt.text?.clear()
            binding.lastnameEt.text?.clear()
            binding.rollnoEt.text?.clear()
            Toast.makeText(this,"Save", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"Fill all the fields",Toast.LENGTH_SHORT).show()
        }

        }
    }

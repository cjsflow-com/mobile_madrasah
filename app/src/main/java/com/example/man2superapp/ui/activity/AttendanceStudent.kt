package com.example.man2superapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.man2superapp.databinding.ActivityAttendanceStudentBinding
import com.example.man2superapp.source.LoginTemp
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AttendanceStudent : AppCompatActivity()
{
    private lateinit var attendanceBinding: ActivityAttendanceStudentBinding
    @Inject
    lateinit var localStore: LoginTemp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attendanceBinding = ActivityAttendanceStudentBinding.inflate(layoutInflater)
        setContentView(attendanceBinding.root)
        setCurrentDate()
    }

    private fun setCurrentDate()
    {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateNow = dateFormat.format(calendar.time)
        attendanceBinding.mtvDateToday.text = dateNow
    }
}
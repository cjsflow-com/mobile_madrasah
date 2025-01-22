package com.example.man2superapp.source.network.response.attendance_student

import com.google.gson.annotations.SerializedName

data class Attendance(
    @SerializedName("id")
    val id: Int,
    @SerializedName("time_attendance_student_in")
    val timeIn: String,
    @SerializedName("time_attendance_student_out")
    val timeOut: String,
    @SerializedName("status_in")
    val statusIn: String,
    @SerializedName("status_out")
    val statusOut: String,
)
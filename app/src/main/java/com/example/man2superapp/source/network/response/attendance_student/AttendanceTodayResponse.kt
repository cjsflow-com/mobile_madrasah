package com.example.man2superapp.source.network.response.attendance_student


import com.google.gson.annotations.SerializedName

data class AttendanceTodayResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("time_attendance_student_in")
    val timeAttendanceStudentIn: String,
    @SerializedName("time_attendance_student_out")
    val timeAttendanceStudentOut: String
)
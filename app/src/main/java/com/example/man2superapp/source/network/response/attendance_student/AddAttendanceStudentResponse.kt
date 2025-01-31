package com.example.man2superapp.source.network.response.attendance_student

import com.google.gson.annotations.SerializedName

data class AddAttendanceStudentResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("position")
    val position: Int,
    @SerializedName("code")
    val code: Int,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("attendance")
    val attendance: Attendance,
    @SerializedName("url")
    val url: String,
)
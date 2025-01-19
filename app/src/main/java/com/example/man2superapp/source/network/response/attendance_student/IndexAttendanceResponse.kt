package com.example.man2superapp.source.network.response.attendance_student


import com.google.gson.annotations.SerializedName

data class IndexAttendanceResponse(
    @SerializedName("attendance")
    val attendance: List<Attendance>,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
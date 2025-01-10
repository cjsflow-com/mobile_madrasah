package com.example.man2superapp.source.network.response.violation


import com.google.gson.annotations.SerializedName

data class SchoolViolationStudentResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("school_violation_student")
    val schoolViolationStudent: List<SchoolViolationStudent>,
    @SerializedName("success")
    val success: Boolean
)
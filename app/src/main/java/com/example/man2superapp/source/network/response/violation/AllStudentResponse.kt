package com.example.man2superapp.source.network.response.violation


import com.google.gson.annotations.SerializedName

data class AllStudentResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("student")
    val student: List<Student>
)
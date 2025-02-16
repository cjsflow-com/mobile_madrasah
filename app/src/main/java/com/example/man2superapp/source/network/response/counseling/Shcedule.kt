package com.example.man2superapp.source.network.response.counseling


import com.google.gson.annotations.SerializedName

data class Shcedule(
    @SerializedName("counselor_name")
    val counselorName: String,
    @SerializedName("date_counseling")
    val dateCounseling: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("session_name")
    val sessionName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("student_name")
    val studentName: String
)
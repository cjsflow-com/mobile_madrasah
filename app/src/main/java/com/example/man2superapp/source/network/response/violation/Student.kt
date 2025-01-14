package com.example.man2superapp.source.network.response.violation


import com.example.man2superapp.source.network.response.login.Student
import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
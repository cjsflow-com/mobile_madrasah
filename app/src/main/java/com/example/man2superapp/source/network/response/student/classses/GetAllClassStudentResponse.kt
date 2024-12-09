package com.example.man2superapp.source.network.response.student.classses


import com.example.man2superapp.source.network.response.student.classses.Data
import com.google.gson.annotations.SerializedName

data class GetAllClassStudentResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
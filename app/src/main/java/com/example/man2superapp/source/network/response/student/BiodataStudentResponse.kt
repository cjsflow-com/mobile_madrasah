package com.example.man2superapp.source.network.response.student


import com.google.gson.annotations.SerializedName

data class BiodataStudentResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
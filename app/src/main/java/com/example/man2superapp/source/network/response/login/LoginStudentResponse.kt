package com.example.man2superapp.source.network.response.login


import com.google.gson.annotations.SerializedName

data class LoginStudentResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("student")
    val student: Student?,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String,
    @SerializedName("token_type")
    val tokenType: String
)
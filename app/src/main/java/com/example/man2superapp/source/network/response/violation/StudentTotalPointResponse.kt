package com.example.man2superapp.source.network.response.violation


import com.google.gson.annotations.SerializedName

data class StudentTotalPointResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("total_points")
    val totalPoints: Int
)
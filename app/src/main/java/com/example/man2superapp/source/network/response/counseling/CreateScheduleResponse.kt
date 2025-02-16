package com.example.man2superapp.source.network.response.counseling


import com.google.gson.annotations.SerializedName

data class CreateScheduleResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
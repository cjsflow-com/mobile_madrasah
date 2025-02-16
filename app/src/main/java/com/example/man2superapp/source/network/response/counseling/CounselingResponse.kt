package com.example.man2superapp.source.network.response.counseling


import com.google.gson.annotations.SerializedName

data class CounselingResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("session_counseling")
    val sessionCounseling: List<SessionCounseling>,
    @SerializedName("success")
    val success: Boolean
)
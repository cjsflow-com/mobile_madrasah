package com.example.man2superapp.source.network.response.counseling


import com.google.gson.annotations.SerializedName

data class CounselorResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("counselor")
    val counselor: List<Counselor>,
    @SerializedName("success")
    val success: Boolean
)
package com.example.man2superapp.source.network.response.counseling


import com.google.gson.annotations.SerializedName

data class SessionCounseling(
    @SerializedName("counseling_name")
    val counselingName: String,
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("start_time")
    val startTime: String,
)
package com.example.man2superapp.source.network.response.counseling


import com.google.gson.annotations.SerializedName

data class GetScheduleResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("success")
    val success: Boolean
)
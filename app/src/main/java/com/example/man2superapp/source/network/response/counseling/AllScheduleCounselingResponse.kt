package com.example.man2superapp.source.network.response.counseling


import com.google.gson.annotations.SerializedName

data class AllScheduleCounselingResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("shcedule")
    val shcedule: List<Shcedule>,
    @SerializedName("success")
    val success: Boolean
)
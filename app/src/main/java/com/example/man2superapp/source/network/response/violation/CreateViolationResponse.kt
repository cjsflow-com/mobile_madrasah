package com.example.man2superapp.source.network.response.violation

import com.google.gson.annotations.SerializedName

data class CreateViolationResponse (
    @SerializedName("code")
    val code: Int,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)
package com.example.man2superapp.source.network.response.violation

import com.google.gson.annotations.SerializedName

data class UpdatePhoneNumberResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
)
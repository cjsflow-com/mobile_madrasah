package com.example.man2superapp.source.network.response.login

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
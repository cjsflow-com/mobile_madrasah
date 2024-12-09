package com.example.man2superapp.source.network.response.users


import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
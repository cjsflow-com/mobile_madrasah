package com.example.man2superapp.source.network.response.users


import com.google.gson.annotations.SerializedName

data class BiodataUserResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
package com.example.man2superapp.source.network.response.student


import com.google.gson.annotations.SerializedName

data class UpdatePasswordStudent(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
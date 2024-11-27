package com.example.man2superapp.source.network.response.songket_emak


import com.google.gson.annotations.SerializedName

data class CreateSongketMother(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
package com.example.man2superapp.source.network.response.songket_emak


import com.google.gson.annotations.SerializedName

data class ListSongketEmakResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("songket_emak")
    val songketEmak: List<SongketEmak>,
    @SerializedName("success")
    val success: Boolean
)
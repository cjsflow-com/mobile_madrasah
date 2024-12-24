package com.example.man2superapp.source.network.response.songket_emak


import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("accepted")
    val accepted: Int,
    @SerializedName("completed")
    val completed: Int,
    @SerializedName("queue")
    val queue: Int,
    @SerializedName("reject")
    val reject: Int
)
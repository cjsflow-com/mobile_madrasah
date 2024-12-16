package com.example.man2superapp.source.network.response.songket_emak.gtk


import com.google.gson.annotations.SerializedName

data class CountStatusResponse(
    @SerializedName("accepted_leader_madrasah")
    val acceptedLeaderMadrasah: Int,
    @SerializedName("done")
    val done: Int,
    @SerializedName("queue")
    val queue: Int,
    @SerializedName("rejected")
    val rejected: Int,
    @SerializedName("request_leader_madrasah")
    val requestLeaderMadrasah: Int
)
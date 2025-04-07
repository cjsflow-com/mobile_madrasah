package com.example.man2superapp.source.network.response.counseling


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("date")
    val date: String,
    @SerializedName("date_iso")
    val dateIso: String,
    @SerializedName("status")
    val status: Boolean
)
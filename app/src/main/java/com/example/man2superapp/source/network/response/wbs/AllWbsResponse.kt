package com.example.man2superapp.source.network.response.wbs


import com.google.gson.annotations.SerializedName

data class AllWbsResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
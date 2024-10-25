package com.example.man2superapp.source.network.response.wbs


import com.google.gson.annotations.SerializedName

data class CreateWbsResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)
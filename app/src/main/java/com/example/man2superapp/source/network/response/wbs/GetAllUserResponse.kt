package com.example.man2superapp.source.network.response.wbs


import com.google.gson.annotations.SerializedName

data class GetAllUserResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: List<DataUser>,
    @SerializedName("message")
    val message: String
)
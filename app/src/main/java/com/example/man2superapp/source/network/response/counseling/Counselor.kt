package com.example.man2superapp.source.network.response.counseling


import com.google.gson.annotations.SerializedName

data class Counselor(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
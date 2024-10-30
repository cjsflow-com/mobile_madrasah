package com.example.man2superapp.source.network.response.wbs

import com.google.gson.annotations.SerializedName

data class DataUser(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
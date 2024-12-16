package com.example.man2superapp.source.network.response


import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String
)
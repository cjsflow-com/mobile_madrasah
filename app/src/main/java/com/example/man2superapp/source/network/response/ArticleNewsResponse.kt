package com.example.man2superapp.source.network.response


import com.google.gson.annotations.SerializedName

data class ArticleNewsResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("article")
    val article: List<Article>,
    @SerializedName("success")
    val success: Boolean
)
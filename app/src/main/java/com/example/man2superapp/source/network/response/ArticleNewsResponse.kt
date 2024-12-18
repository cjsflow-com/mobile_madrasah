package com.example.man2superapp.source.network.response


import com.google.gson.annotations.SerializedName

data class ArticleNewsResponse(
    @SerializedName("article")
    val article: List<Article>,
    @SerializedName("code")
    val code: Int,
    @SerializedName("success")
    val success: Boolean
)
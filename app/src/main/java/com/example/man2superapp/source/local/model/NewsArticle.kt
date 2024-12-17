package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.Article

data class NewsArticle(
    val id: Int,
    val title: String,
    val image: String
)

fun List<Article>.toGenerateAllListArticle(): MutableList<NewsArticle>
{
    val listAllArticle = mutableListOf<NewsArticle>()
    this.forEach { listAllArticle.add(it.toNewsArticle()) }
    return  listAllArticle
}

fun Article.toNewsArticle(): NewsArticle{
    return NewsArticle(
        id = this.id?: 0,
        title = this.title?: "?",
        image = this.image?: "?"
    )
}
package com.example.newsapp.api.model

import java.io.Serializable

data class NewsResponse(
    val articles: List<News>?,
    val status: String?,
    val totalResults: Int?
)
data class News(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
): Serializable

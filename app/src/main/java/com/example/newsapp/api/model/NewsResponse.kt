package com.example.newsapp.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class NewsResponse(
    val articles: List<News>?,
    val status: String?,
    val totalResults: Int?
)

@Entity
data class News(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Int,
    @ColumnInfo
    val author: String?,
    @ColumnInfo
    val content: String?,
    @ColumnInfo
    val description: String?,
    @ColumnInfo
    val publishedAt: String?,
    @ColumnInfo
    val source: Source?,
    @ColumnInfo
    val title: String,
    val url: String?,
    @ColumnInfo
    val urlToImage: String?
): Serializable

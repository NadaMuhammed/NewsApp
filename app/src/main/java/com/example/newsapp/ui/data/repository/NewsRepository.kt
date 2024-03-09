package com.example.newsapp.ui.data.repository

import com.example.newsapp.api.model.News
import com.example.newsapp.api.model.Source

interface NewsRepository {
    suspend fun loadSources(categoryId: String): List<Source?>
    suspend fun loadNews(sourceId: String, query: String): List<News?>
}
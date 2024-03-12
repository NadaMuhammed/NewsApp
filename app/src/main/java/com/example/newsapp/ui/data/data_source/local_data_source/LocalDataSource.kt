package com.example.newsapp.ui.data.data_source.local_data_source

import com.example.newsapp.api.model.News
import com.example.newsapp.api.model.Source

interface LocalDataSource {
    suspend fun loadSources(category: String): List<Source?>
    suspend fun loadNews(source: String): List<News?>
    suspend fun saveSources(sources: List<Source?>)
    suspend fun saveNews(news: List<News?>)
}
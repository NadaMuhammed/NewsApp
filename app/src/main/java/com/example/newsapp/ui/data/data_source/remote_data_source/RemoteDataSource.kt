package com.example.newsapp.ui.data.data_source.remote_data_source

import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.SourcesResponse

interface RemoteDataSource {
    suspend fun loadSources(category: String): SourcesResponse
    suspend fun loadNews(source: String, query: String): NewsResponse
}
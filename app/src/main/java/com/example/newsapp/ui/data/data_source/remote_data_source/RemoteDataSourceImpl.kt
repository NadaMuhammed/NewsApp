package com.example.newsapp.ui.data.data_source.remote_data_source

import com.example.newsapp.Constants
import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.api.retrofit.ApiManager

class RemoteDataSourceImpl : RemoteDataSource {
    override suspend fun loadSources(category: String): SourcesResponse {
        return ApiManager.webServices.getSources(Constants.API_KEY, category)
    }

    override suspend fun loadNews(source: String, query: String): NewsResponse {
        return ApiManager.webServices.getNews(Constants.API_KEY, source, query)
    }
}
package com.example.newsapp.ui.data.repository

import com.example.newsapp.ConnectivityChecker
import com.example.newsapp.api.model.News
import com.example.newsapp.api.model.Source
import com.example.newsapp.ui.data.data_source.local_data_source.LocalDataSource
import com.example.newsapp.ui.data.data_source.remote_data_source.RemoteDataSource

class NewsRepositoryImpl(val remoteDataSource: RemoteDataSource,
                   val localDataSource: LocalDataSource): NewsRepository {
    override suspend fun loadNews(sourceId: String, query: String): List<News?> {
        if(ConnectivityChecker.isNetworkAvailable()){
            val response = remoteDataSource.loadNews(sourceId, query)
            response.articles?.let {
                localDataSource.saveNews(it)
            }
            return response.articles!!
        }else {
            return localDataSource.loadNews(sourceId)
        }
    }

    override suspend fun loadSources(categoryId: String): List<Source?> {
        if(ConnectivityChecker.isNetworkAvailable()){
            val response = remoteDataSource.loadSources(categoryId)
            response.sources?.let {
                localDataSource.saveSources(it)
            }
            return response.sources!!
        }else {
            return localDataSource.loadSources(categoryId)
        }
    }
}
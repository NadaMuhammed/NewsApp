package com.example.newsapp.ui.data.data_source.local_data_source

import com.example.newsapp.api.model.News
import com.example.newsapp.api.model.Source
import com.example.newsapp.database.MyDatabase

class LocalDataSourceImpl(val database: MyDatabase): LocalDataSource {
    override suspend fun loadSources(category: String): List<Source> {
        return database.sourcesDao().getSources(category)
    }

    override suspend fun loadNews(source: String): List<News> {
        return database.newsDao().getNews(source)
    }

    override suspend fun saveSources(sources: List<Source?>) {
        val nonNullableList = sources.filter {
            return@filter it != null
        } as List<Source>
        database.sourcesDao().addSources(nonNullableList)
    }

    override suspend fun saveNews(news: List<News?>) {
        val nonNullableList = news.filter {
            return@filter it != null
        } as List<News>
        database.newsDao().addNews(nonNullableList)
    }
}
package com.example.newsapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.api.model.News
import com.example.newsapp.api.model.Source

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNews(news: List<News>)

    @Query("select * from News where source = :source ")
    fun getNews(source: String): List<News>
}
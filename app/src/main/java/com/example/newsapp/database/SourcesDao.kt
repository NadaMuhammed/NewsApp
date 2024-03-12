package com.example.newsapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.api.model.Source

@Dao
interface SourcesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSources(sources: List<Source>)

    @Query("select * from source where category = :category")
    fun getSources(category: String): List<Source>
}
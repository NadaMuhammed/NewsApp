package com.example.newsapp.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class SourcesResponse(
    var status: String?,
    var sources: List<Source>?
)

@Entity
data class Source(
    @PrimaryKey
    @ColumnInfo
    var id: String,
    @ColumnInfo
    var name: String?,
    @ColumnInfo
    var description: String?,
    var url: String?,
    @ColumnInfo
    var category: String?,
    var language: String?,
    var country: String?
) : Serializable
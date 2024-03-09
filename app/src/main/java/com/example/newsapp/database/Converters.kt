package com.example.newsapp.database

import androidx.room.TypeConverter
import com.example.newsapp.api.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source?): String? {
        return source?.id
    }

    @TypeConverter
    fun toSource(id: String): Source {
        return Source(id, id, id, id, id, id, id)
    }
}
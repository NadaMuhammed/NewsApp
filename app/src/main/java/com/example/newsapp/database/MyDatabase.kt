package com.example.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.api.model.News
import com.example.newsapp.api.model.Source

@Database(entities = [Source::class, News::class], version = 3)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun sourcesDao(): SourcesDao
    abstract fun newsDao(): NewsDao

    companion object{
        private var myDataBase: MyDatabase? = null
        fun init(context: Context){
            if(myDataBase == null){
                myDataBase =
                    Room.databaseBuilder(context, MyDatabase::class.java, "My_database")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()

            }
        }
        fun getInstance(): MyDatabase{
            return myDataBase!!;
        }
    }
}
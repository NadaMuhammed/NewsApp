package com.example.newsapp

import android.app.Application
import com.example.newsapp.database.MyDatabase

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ConnectivityChecker.context = this
        MyDatabase.init(this)
    }
}
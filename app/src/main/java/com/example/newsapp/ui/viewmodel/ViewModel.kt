package com.example.newsapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Constants
import com.example.newsapp.api.model.News
import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.Source
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.api.retrofit.ApiManager
import com.example.newsapp.database.MyDatabase
import com.example.newsapp.ui.adapters.Category
import com.example.newsapp.ui.data.data_source.local_data_source.LocalDataSourceImpl
import com.example.newsapp.ui.data.data_source.remote_data_source.RemoteDataSourceImpl
import com.example.newsapp.ui.data.repository.NewsRepository
import com.example.newsapp.ui.data.repository.NewsRepositoryImpl
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel : ViewModel() {
    val newsRepo: NewsRepository = NewsRepositoryImpl(
        RemoteDataSourceImpl(), LocalDataSourceImpl(
        MyDatabase.getInstance())
    )
    val sourcesLiveData: MutableLiveData<List<Source?>?> = MutableLiveData()
    val newsLiveData: MutableLiveData<List<News?>?> = MutableLiveData()
    val errorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressBarLiveData: MutableLiveData<Boolean> = MutableLiveData()
    fun loadSources(category: Category) {
        progressBarLiveData.value = true
        errorLiveData.value = false
        viewModelScope.launch {
            try {
                val sources = newsRepo.loadSources(category.id)
                progressBarLiveData.value = false
                sourcesLiveData.value = sources
            } catch (e: Throwable) {
                progressBarLiveData.value = false
                errorLiveData.value = true
                Log.e("exception","$e")
            }
        }
    }

    fun loadNews(sourceId: String, query: String) {
        errorLiveData.value = false
        try {
            viewModelScope.launch {
                val news = newsRepo.loadNews(sourceId, query)
                progressBarLiveData.value = false
                newsLiveData.value = news
            }
        } catch (e: Throwable) {
            errorLiveData.value = true
            Log.e("exception","$e")
        }
    }
}
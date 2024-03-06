package com.example.newsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Constants
import com.example.newsapp.api.model.News
import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.Source
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.api.retrofit.ApiManager
import com.example.newsapp.ui.adapters.Category
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel : ViewModel() {
    val sourcesLiveData: MutableLiveData<List<Source>?> = MutableLiveData()
    val newsLiveData: MutableLiveData<List<News>?> = MutableLiveData()
    val errorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressBarLiveData: MutableLiveData<Boolean> = MutableLiveData()
    fun loadSources(category: Category) {
        progressBarLiveData.postValue(true)
        errorLiveData.postValue(false)
        viewModelScope.launch {
            try {
                val sources = ApiManager.webServices.getSources(Constants.API_KEY, category.id).sources
                progressBarLiveData.postValue(false)
                sourcesLiveData.postValue(sources)
            } catch (e: Exception) {
                progressBarLiveData.postValue(false)
                errorLiveData.postValue(true)
            }
        }
    }

    fun loadNews(sourceId: String, query: String) {
        errorLiveData.postValue(false)
        try {
            viewModelScope.launch {
                val news = ApiManager.webServices.getNews(Constants.API_KEY, sourceId, query).articles
                progressBarLiveData.postValue(false)
                newsLiveData.postValue(news)
            }
        } catch (e: Exception) {
            errorLiveData.postValue(true)
        }
    }
}
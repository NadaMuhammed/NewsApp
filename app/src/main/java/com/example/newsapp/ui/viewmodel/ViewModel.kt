package com.example.newsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.Constants
import com.example.newsapp.api.model.News
import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.Source
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.api.retrofit.ApiManager
import com.example.newsapp.ui.adapters.Category
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
        ApiManager.webServices.getSources(Constants.API_KEY, category.id)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                   progressBarLiveData.postValue(false)
                    if (response.isSuccessful) {
                        sourcesLiveData.postValue(response.body()?.sources)
                    } else {
//                        val errorResponse = Gson().fromJson(
//                            response.errorBody()?.string(),
//                            SourcesResponse::class.java
//                        )
                        errorLiveData.postValue(true)
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    progressBarLiveData.postValue(false)
                    errorLiveData.postValue(true)
                }

            })
    }

    fun loadNews(sourceId: String) {
        errorLiveData.postValue(false)
        ApiManager.webServices.getNews(Constants.API_KEY, sourceId, "")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progressBarLiveData.postValue(false)
                    if (response.isSuccessful && response.body()?.articles?.isNotEmpty() == true) {
                        newsLiveData.postValue(response.body()?.articles!!)
                    } else {
                        errorLiveData.postValue(true)
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progressBarLiveData.postValue(false)
                    errorLiveData.postValue(true)
                }
            })
    }
}
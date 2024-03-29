package com.example.newsapp.api.retrofit

import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WebServices {
    @GET("/v2/top-headlines/sources")
    suspend fun getSources(@Query("apiKey") apiKey: String, @Query("category") category: String): SourcesResponse

    @GET("/v2/everything")
    suspend fun getNews(@Query("apiKey") apiKey: String, @Query("sources") sources: String, @Query("q") q: String): NewsResponse
}
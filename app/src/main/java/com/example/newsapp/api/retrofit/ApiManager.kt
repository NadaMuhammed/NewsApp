package com.example.newsapp.api.retrofit

import android.util.Log
import com.example.newsapp.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {
    private val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
        Log.e("ApiManager", "Body: $it")
    }).setLevel(HttpLoggingInterceptor.Level.BODY)

    private val retrofit = Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
        .build()

    val webServices: WebServices = retrofit.create(WebServices::class.java)

//    private var retrofit: Retrofit? = null
//
//    fun getWebServices(): WebServices {
//        if (retrofit == null) {
//            retrofit = Retrofit.Builder()
//                .baseUrl(Constants.API_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(
//                    OkHttpClient.Builder()
//                        .addInterceptor(loggingInterceptor).build()
//                )
//                .build()
//        }
//
//        return retrofit!!.create(WebServices::class.java)
//    }
}
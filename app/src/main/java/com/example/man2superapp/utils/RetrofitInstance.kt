package com.example.man2superapp.utils

import com.example.man2superapp.source.network.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val articleRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constant.ARTICLE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val articleApiService: ApiService by lazy {
        articleRetrofit.create(ApiService::class.java)
    }
}
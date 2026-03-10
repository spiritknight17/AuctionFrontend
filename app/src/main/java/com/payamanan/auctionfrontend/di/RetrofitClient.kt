package com.payamanan.auctionfrontend.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val USER_BASE_URL = "http://127.0.0.1:8081/"
    private const val AUCT_BASE_URL = "http://127.0.0.1:8080/"

    val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }).build();

    val auct_instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(AUCT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val user_instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(USER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}
package com.example.joshqinshop.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
   const val BASE_URL = "https://407b-86-62-2-178.ngrok-free.app"




    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .client(OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor()).build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}
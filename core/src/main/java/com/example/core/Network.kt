package com.example.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {

    fun initRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://hello-world.innocv.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val okHttpClient = OkHttpClient.Builder().build()

}
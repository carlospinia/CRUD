package com.example.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {

    fun initRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://hello-world.innocv.com/api/User/")
        .client(initOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun initOkHttpClient() = OkHttpClient.Builder().build()

}
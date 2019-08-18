package com.example.data

import com.example.domain.User
import retrofit2.Response
import retrofit2.http.*

interface UserRetrofit {

    @GET(".")
    suspend fun getUserList(): Response<List<User>>

    @GET("{id}")
    suspend fun getUser(@Path("id") id: Int): Response<User>

    @POST(".")
    suspend fun addUser(@Body user: User): Response<Unit>

    @PUT(".")
    suspend fun editUser(@Body user: User): Response<Unit>

    @DELETE("{id}")
    suspend fun deleteUser(@Path("id") userId: Int): Response<Unit>
}
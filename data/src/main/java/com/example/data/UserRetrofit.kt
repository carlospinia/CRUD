package com.example.data

import com.example.domain.User
import retrofit2.Response
import retrofit2.http.*

interface UserRetrofit {

    @GET("api/User/")
    suspend fun getUserList(): Response<List<User>>

    @GET("api/User/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<User>

    @POST("api/User/")
    suspend fun addUser(@Body user: User): Response<Unit>

    @PUT("api/User/")
    suspend fun editUser(@Body user: User): Response<Unit>

    @DELETE("api/User/{id}")
    suspend fun deleteUser(@Path("id") userId: Int): Response<Unit>
}
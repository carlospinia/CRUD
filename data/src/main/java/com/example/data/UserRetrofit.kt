package com.example.data

import com.example.domain.User
import retrofit2.Response
import retrofit2.http.*

interface UserRetrofit {

    @GET(".")
    suspend fun getUserList(): Response<List<User>>

    @GET("{id}")
    suspend fun getUser(@Path("id") id: Int)

    @POST
    suspend fun addUser()

    @PUT
    suspend fun editUser()

    @DELETE
    suspend fun deleteUser()
}
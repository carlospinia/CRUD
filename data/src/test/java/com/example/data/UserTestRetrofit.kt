package com.example.data

import com.example.domain.User
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.`when`
import retrofit2.Response
import org.junit.Assert.assertEquals

class UserTestRetrofit {

    private val userRetrofit: UserRetrofit = mock()

    @Test
    fun `Get user list - return a user list`(){

        val expectedResponse = listOf(
            User("Piña", "1993-06-22T00:00:00", 2740),
            User("Pablo", "2018-01-01T00:00:00", 2748)
        )

        val expectedResult : Response<List<User>> = Response.success(expectedResponse)

        runBlocking { `when`(userRetrofit.getUserList()).thenReturn(expectedResult) }

        val response = runBlocking { userRetrofit.getUserList() }

        assertEquals(response.body(), expectedResponse)
    }

    @Test
    fun `Get user - return a user`(){

        val expectedResponse = User("Piña", "1993-06-22T00:00:00", 2740)

        val expectedResult : Response<User> = Response.success(expectedResponse)

        runBlocking { `when`(userRetrofit.getUser(0)).thenReturn(expectedResult) }

        val response = runBlocking { userRetrofit.getUser(0) }

        assertEquals(response.body(), expectedResponse)
    }

    @Test
    fun `Delete user - return Unit`(){
        val expectedResult : Response<Unit> = Response.success(Unit)

        runBlocking { `when`(userRetrofit.deleteUser(0)).thenReturn(expectedResult) }

        val response = runBlocking { userRetrofit.deleteUser(0) }

        assertEquals(response.body(), Unit)
    }

    @Test
    fun `Edit user - return Unit`(){

        val body = User("Piña", "1993-06-22T00:00:00", 2740)

        val expectedResult : Response<Unit> = Response.success(Unit)

        runBlocking { `when`(userRetrofit.editUser(body)).thenReturn(expectedResult) }

        val response = runBlocking { userRetrofit.editUser(body) }

        assertEquals(response.body(), Unit)
    }

    @Test
    fun `Add user - return Unit`(){

        val body = User("Piña", "1993-06-22T00:00:00", 2740)

        val expectedResult : Response<Unit> = Response.success(Unit)

        runBlocking { `when`(userRetrofit.addUser(body)).thenReturn(expectedResult) }

        val response = runBlocking { userRetrofit.addUser(body) }

        assertEquals(response.body(), Unit)
    }
}
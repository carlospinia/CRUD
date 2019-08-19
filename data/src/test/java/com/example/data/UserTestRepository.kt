package com.example.data

import com.example.core.Either
import com.example.core.Failure
import com.example.domain.User
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Test
import org.mockito.Mockito.`when`
import retrofit2.Response
import org.junit.Assert.assertEquals
import org.junit.internal.runners.statements.Fail

class UserTestRepository {

    private val userRetrofit: UserRetrofit = mock()
    private val userRepository = UserRepositoryImpl(userRetrofit)

    @Test
    fun `getUserList success - Returns list of users `(){

        val expectedResponse = listOf(
            User("Piña", "1993-06-22T00:00:00", 2740),
            User("Pablo", "2018-01-01T00:00:00", 2748)
        )
        val expectedResult : Response<List<User>> = Response.success(expectedResponse)

        runBlocking { `when`(userRetrofit.getUserList()).thenReturn(expectedResult) }

        val response : Either<Failure, List<User>> = runBlocking { userRepository.getUserList() }

        assertEquals(response, Either.Right(expectedResponse))
    }

    @Test
    fun `getUserList server error`(){

        val responseBody : ResponseBody = mock()
        val expectedResult : Response<List<User>> = Response.error(500, responseBody)

        runBlocking { `when`(userRetrofit.getUserList()).thenReturn(expectedResult) }

        val response : Either<Failure, List<User>> = runBlocking { userRepository.getUserList() }

        assertEquals(response, Either.Left(Failure.ServerError))
    }

    @Test
    fun `getUserList server error for catch exception`(){

        runBlocking { `when`(userRetrofit.getUserList()).thenReturn(null) }

        val response : Either<Failure, List<User>> = runBlocking { userRepository.getUserList() }

        assertEquals(response, Either.Left(Failure.ServerError))
    }


    @Test
    fun `add user success`(){

        val user = User("Piña", "1993-06-22T00:00:00", 2740)

        val expectedResult : Response<Unit> = Response.success(Unit)

        runBlocking { `when`(userRetrofit.addUser(user)).thenReturn(expectedResult) }

        val response : Either<Failure, Unit> = runBlocking { userRepository.createUser(user) }

        assertEquals(response, Either.Left(Unit))
    }

    @Test
    fun `add user server error`(){

        val user = User("Piña", "1993-06-22T00:00:00", 2740)

        val responseBody : ResponseBody = mock()
        val expectedResult : Response<Unit> = Response.error(500, responseBody)

        runBlocking { `when`(userRetrofit.addUser(user)).thenReturn(expectedResult) }

        val response : Either<Failure, Unit> = runBlocking { userRepository.createUser(user) }

        assertEquals(response, Either.Left(Failure.ServerError))
    }

    @Test
    fun `create user server error for catch exception`(){

        val user = User("Piña", "1993-06-22T00:00:00", 2740)

        runBlocking { `when`(userRetrofit.addUser(user)).thenReturn(null) }

        val response : Either<Failure, Unit> = runBlocking { userRepository.createUser(user) }

        assertEquals(response, Either.Left(Failure.ServerError))
    }

    //TODO Similar test fot getUser, editUser and deleteUser

}
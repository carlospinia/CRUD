package com.example.domain

import com.example.core.Either
import com.example.core.Failure
import com.example.domain.createUser.CreateUser
import com.example.domain.deleteUser.DeleteUser
import com.example.domain.editUser.EditUser
import com.example.domain.getUser.GetUser
import com.example.domain.loadUserList.LoadUserList
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`

class UserTest {

    private val userRepository: UserRepository = mock()

    private val createUser = CreateUser(userRepository)
    private val deleteUser = DeleteUser(userRepository)
    private val editUser = EditUser(userRepository)
    private val getUser = GetUser(userRepository)
    private val loadUserList = LoadUserList(userRepository)

    @Test
    fun `Uses the UserRepository to create an user`() = runBlocking {
        val expectedResult: Either<Failure, Unit> = mock()
        val user = User("user", "birthdate", 0)

        `when`(userRepository.createUser(user)).thenReturn(expectedResult)

        assertEquals(expectedResult, createUser.run(user))
    }

    @Test
    fun `Uses the UserRepository to delete an user`() = runBlocking {
        val expectedResult: Either<Failure, Unit> = mock()

        `when`(userRepository.deleteUser(0)).thenReturn(expectedResult)

        assertEquals(expectedResult, deleteUser.run(0))
    }

    @Test
    fun `Uses the UserRepository to edit an user`() = runBlocking {
        val expectedResult: Either<Failure, Unit> = mock()
        val user = User("user", "birthdate", 0)

        `when`(userRepository.editUser(user)).thenReturn(expectedResult)

        assertEquals(expectedResult, editUser.run(user))
    }

    @Test
    fun `Uses the UserRepository to get an user`() = runBlocking {
        val expectedResult: Either<Failure, User> = mock()

        `when`(userRepository.getUser(0)).thenReturn(expectedResult)

        assertEquals(expectedResult, getUser.run(0))
    }

    @Test
    fun `Uses the UserRepository to load an user list`() = runBlocking {
        val expectedResult: Either<Failure, List<User>> = mock()

        `when`(userRepository.getUserList()).thenReturn(expectedResult)

        assertEquals(expectedResult, loadUserList.run(Unit))
    }
}
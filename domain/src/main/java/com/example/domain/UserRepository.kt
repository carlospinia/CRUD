package com.example.domain

import com.example.core.Either

interface UserRepository {

    suspend fun getUserList() : Either<UserListFailure, List<User>>

    suspend fun createUser(user: User): Either<UserFailure, Unit>

    suspend fun editUser(user: User): Either<UserFailure, Unit>

    suspend fun getUser(userId: Int): Either<UserFailure, User>

    suspend fun deleteUser(userId: Int): Either<UserFailure, Unit>

}
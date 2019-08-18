package com.example.domain

import com.example.core.Either
import com.example.core.Failure

interface UserRepository {

    suspend fun getUserList() : Either<Failure, List<User>>

    suspend fun createUser(user: User): Either<Failure, Unit>

    suspend fun editUser(user: User): Either<Failure, Unit>

    suspend fun getUser(userId: Int): Either<Failure, User>

    suspend fun deleteUser(userId: Int): Either<Failure, Unit>

}
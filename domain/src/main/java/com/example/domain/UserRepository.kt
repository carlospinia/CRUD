package com.example.domain

import com.example.core.Either
import com.example.core.Failure

interface UserRepository {

    suspend fun getUserList() : Either<Failure, List<User>>

}
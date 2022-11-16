package com.example.domain.createUser

import com.example.core.Either
import com.example.core.UseCase
import com.example.domain.User
import com.example.domain.UserFailure
import com.example.domain.UserRepository

class CreateUser(private val userRepository: UserRepository): UseCase<UserFailure, Unit, User>(){
    override suspend fun run(params: User): Either<UserFailure, Unit> = userRepository.createUser(params)
}
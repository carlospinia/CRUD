package com.example.domain.getUser

import com.example.core.Either
import com.example.core.UseCase
import com.example.domain.User
import com.example.domain.UserFailure
import com.example.domain.UserRepository

class GetUser(private val userRepository: UserRepository) : UseCase<UserFailure, User, Int>() {

    override suspend fun run(params: Int): Either<UserFailure, User> = userRepository.getUser(params)

}
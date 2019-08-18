package com.example.domain.getUser

import com.example.core.Either
import com.example.core.Failure
import com.example.core.UseCase
import com.example.domain.User
import com.example.domain.UserRepository

class GetUser(private val userRepository: UserRepository) : UseCase<Failure, User, Int>() {

    override suspend fun run(params: Int): Either<Failure, User> = userRepository.getUser(params)

}
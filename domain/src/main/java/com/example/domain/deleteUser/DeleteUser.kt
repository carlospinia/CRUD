package com.example.domain.deleteUser

import com.example.core.Either
import com.example.core.UseCase
import com.example.domain.UserFailure
import com.example.domain.UserRepository

class DeleteUser(private val userRepository: UserRepository) : UseCase<UserFailure, Unit, Int>() {

    override suspend fun run(params: Int): Either<UserFailure, Unit> = userRepository.deleteUser(params)

}
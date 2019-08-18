package com.example.domain.deleteUser

import com.example.core.Either
import com.example.core.Failure
import com.example.core.UseCase
import com.example.domain.User
import com.example.domain.UserRepository

class DeleteUser(private val userRepository: UserRepository) : UseCase<Failure, Unit, Int>() {

    override suspend fun run(params: Int): Either<Failure, Unit> = userRepository.deleteUser(params)

}
package com.example.domain.loadUserList

import com.example.core.Either
import com.example.core.Failure
import com.example.core.UseCase
import com.example.domain.User
import com.example.domain.UserRepository

class LoadUserList(private val userRepository: UserRepository) : UseCase<Failure, List<User>, Unit>() {

    override suspend fun run(params: Unit): Either<Failure, List<User>> = userRepository.getUserList()

}
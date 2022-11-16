package com.example.domain.loadUserList

import com.example.core.Either
import com.example.core.UseCase
import com.example.domain.User
import com.example.domain.UserListFailure
import com.example.domain.UserRepository

class LoadUserList(private val userRepository: UserRepository) : UseCase<UserListFailure, List<User>, Unit>() {

    override suspend fun run(params: Unit): Either<UserListFailure, List<User>> = userRepository.getUserList()

}
package com.example.domain.editUser

import com.example.core.Either
import com.example.core.Failure
import com.example.core.UseCase
import com.example.domain.User
import com.example.domain.UserRepository

class EditUser(private val userRepository: UserRepository): UseCase<Failure, Unit, User>(){
    override suspend fun run(params: User): Either<Failure, Unit> = userRepository.editUser(params)
}
package com.example.data

import com.example.core.Either
import com.example.core.Failure
import com.example.domain.User
import com.example.domain.UserRepository
import kotlinx.coroutines.delay

class UserRepositoryImpl: UserRepository {
    override suspend fun getUserList(): Either<Failure, List<User>> {
        delay(1500)

        val userList = mutableListOf<User>()
        for (i in 0..15){
            userList.add(User("name$i","birthdate$i", i))
        }

        return Either.Right(userList)
    }
}
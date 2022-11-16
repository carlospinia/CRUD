package com.example.data

import com.example.core.Either
import com.example.domain.User
import com.example.domain.UserFailure
import com.example.domain.UserListFailure
import com.example.domain.UserRepository

class UserRepositoryImpl(private val userRetrofit: UserRetrofit): UserRepository {

    override suspend fun getUserList(): Either<UserListFailure, List<User>> {
        return try {
            val response = userRetrofit.getUserList()
            when (response.isSuccessful){
                true -> Either.Right(response.body()!!)
                false -> Either.Left(UserListFailure.ServerError)
            }
        } catch (e: Exception){
            println("BOOM $e")
            Either.Left(UserListFailure.ServerError)
        }
    }

    override suspend fun createUser(user: User): Either<UserFailure, Unit> {
        return try {
            val response = userRetrofit.addUser(user)
            when (response.isSuccessful){
                true -> Either.Right(response.body()!!)
                false -> Either.Left(UserFailure.ServerError)
            }
        } catch (e: Exception){
            Either.Left(UserFailure.ServerError)
        }
    }

    override suspend fun editUser(user: User): Either<UserFailure, Unit> {
        return try {
            val response = userRetrofit.editUser(user)
            when (response.isSuccessful){
                true -> Either.Right(response.body()!!)
                false -> Either.Left(UserFailure.ServerError)
            }
        } catch (e: Exception){
            Either.Left(UserFailure.ServerError)
        }
    }

    override suspend fun getUser(userId: Int): Either<UserFailure, User> {
        return try {
            val response = userRetrofit.getUser(userId)
            when (response.isSuccessful){
                true -> Either.Right(response.body()!!)
                false -> Either.Left(UserFailure.ServerError)
            }
        } catch (e: Exception){
            Either.Left(UserFailure.ServerError)
        }
    }

    override suspend fun deleteUser(userId: Int): Either<UserFailure, Unit> {
        return try {
            val response = userRetrofit.deleteUser(userId)
            when (response.isSuccessful){
                true -> Either.Right(response.body()!!)
                false -> Either.Left(UserFailure.ServerError)
            }
        } catch (e: Exception){
            Either.Left(UserFailure.ServerError)
        }
    }
}
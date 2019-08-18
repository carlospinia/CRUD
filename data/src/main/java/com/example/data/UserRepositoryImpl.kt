package com.example.data

import com.example.core.Either
import com.example.core.Failure
import com.example.domain.User
import com.example.domain.UserRepository

class UserRepositoryImpl(private val userRetrofit: UserRetrofit): UserRepository {

    override suspend fun getUserList(): Either<Failure, List<User>> {
        return try {
            val response = userRetrofit.getUserList()
            when (response.isSuccessful){
                true -> Either.Right(response.body()!!)
                false -> Either.Left(Failure.ServerError)
            }
        } catch (e: Exception){
            Either.Left(Failure.ServerError)
        }
    }

    override suspend fun createUser(user: User): Either<Failure, Unit> {
        return try {
            val response = userRetrofit.addUser(user)
            when (response.isSuccessful){
                true -> Either.Right(response.body()!!)
                false -> Either.Left(Failure.ServerError)
            }
        } catch (e: Exception){
            Either.Left(Failure.ServerError)
        }
    }

    override suspend fun editUser(user: User): Either<Failure, Unit> {
        return try {
            val response = userRetrofit.editUser(user)
            when (response.isSuccessful){
                true -> Either.Right(response.body()!!)
                false -> Either.Left(Failure.ServerError)
            }
        } catch (e: Exception){
            Either.Left(Failure.ServerError)
        }
    }

    override suspend fun getUser(userId: Int): Either<Failure, User> {
        return try {
            val response = userRetrofit.getUser(userId)
            when (response.isSuccessful){
                true -> Either.Right(response.body()!!)
                false -> Either.Left(Failure.ServerError)
            }
        } catch (e: Exception){
            Either.Left(Failure.ServerError)
        }
    }

    override suspend fun deleteUser(userId: Int): Either<Failure, Unit> {
        return try {
            val response = userRetrofit.deleteUser(userId)
            when (response.isSuccessful){
                true -> Either.Right(response.body()!!)
                false -> Either.Left(Failure.ServerError)
            }
        } catch (e: Exception){
            Either.Left(Failure.ServerError)
        }
    }
}
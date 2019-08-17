package com.example.data

import com.example.core.Network
import com.example.domain.UserRepository
import org.koin.dsl.module.module

class KoinData {

    val dataModule by lazy {
        module {
            single { Network.initRetrofit().create(UserRetrofit::class.java) }
            single<UserRepository>{ UserRepositoryImpl(get()) }
        }
    }
}
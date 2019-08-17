package com.example.data

import com.example.domain.UserRepository
import org.koin.dsl.module.module

class KoinData {

    val dataModule by lazy {
        module {
            single<UserRepository>{ UserRepositoryImpl() }
        }
    }
}
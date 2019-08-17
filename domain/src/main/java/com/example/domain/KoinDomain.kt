package com.example.domain

import com.example.domain.getUserList.LoadUserList
import org.koin.dsl.module.module

class KoinDomain {
    val domainModule by lazy {
        module {
            factory { LoadUserList(get()) }
        }
    }
}
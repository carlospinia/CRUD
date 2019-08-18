package com.example.domain

import com.example.domain.createUser.CreateUser
import com.example.domain.deleteUser.DeleteUser
import com.example.domain.editUser.EditUser
import com.example.domain.getUser.GetUser
import com.example.domain.loadUserList.LoadUserList
import org.koin.dsl.module.module

class KoinDomain {
    val domainModule by lazy {
        module {
            factory { LoadUserList(get()) }
            factory { CreateUser(get()) }
            factory { EditUser(get()) }
            factory { GetUser(get()) }
            factory { DeleteUser(get()) }
        }
    }
}
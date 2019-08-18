package com.example.crud

import com.example.crud.userDetail.UserDetailVM
import com.example.crud.userList.UserListVM
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class KoinApp {

    val appModule by lazy {
        module {
            viewModel { UserListVM(androidApplication(), get()) }
            viewModel { UserDetailVM(androidApplication()) }
        }
    }
}
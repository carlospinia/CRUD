package com.example.crud

import android.app.Application
import org.koin.android.ext.android.startKoin

class CRUDAplication : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin(
            this,
            listOf(
                KoinApp().appModule
            )
        )
    }
}
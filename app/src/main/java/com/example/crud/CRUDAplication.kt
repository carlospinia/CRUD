package com.example.crud

import android.app.Application
import com.example.data.KoinData
import com.example.domain.KoinDomain
import org.koin.android.ext.android.startKoin

class CRUDAplication : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin(
            this,
            listOf(
                KoinApp().appModule,
                KoinDomain().domainModule,
                KoinData().dataModule
            )
        )
    }
}
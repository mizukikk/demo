package com.example.demo.di

import android.app.Application
import org.koin.core.context.startKoin

class DemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(koinModule)
        }
    }
}
package com.example.foodshop

import android.app.Application
import com.example.foodshop.di.FirebaseFirestoreCommon
import com.example.foodshop.di.FirebaseFirestoreDatabase
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class WorkerShopApplication : Application()  {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@WorkerShopApplication)
            modules(FirebaseFirestoreDatabase)

        }
    }
}
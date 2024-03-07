package com.example.only_friends

import android.app.Application
import com.example.only_friends.database.AppDatabase
import com.example.only_friends.modules.coreModules
import com.example.only_friends.modules.firebaseModule
import com.example.only_friends.modules.roomDbModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OnlyFriendsApplication : Application() {
    override fun onCreate() {
        super.onCreate()


        // Start Koin
        startKoin{
            androidContext(this@OnlyFriendsApplication)
            modules(listOf(coreModules, firebaseModule , roomDbModules))
        }
    }
}
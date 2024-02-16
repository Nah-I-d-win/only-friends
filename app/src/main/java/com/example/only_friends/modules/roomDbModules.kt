package com.example.only_friends.modules

import com.example.only_friends.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


internal val roomDbModules = module {
    single { AppDatabase.getDatabase(androidContext()) }
    single { get<AppDatabase>().userDao() }

}
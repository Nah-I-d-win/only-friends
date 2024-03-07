package com.example.only_friends.modules

import com.example.only_friends.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


internal val roomDbModules = module {
    // nous permet de créer une instance de AppDatabase
    single { AppDatabase.getDatabase(androidContext()) }
    // nous permet de créer une instance de UserDao
    single { get<AppDatabase>().userDao() }

}
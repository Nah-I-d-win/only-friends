package com.example.only_friends.modules


import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

internal val firebaseModule = module {
    //inject singleton for Firebase Auth
    single { FirebaseAuth.getInstance() }
}
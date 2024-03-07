package com.example.only_friends.modules

import com.example.only_friends.repository.userRepository
import com.example.only_friends.viewModel.UserViewModel
import com.example.only_friends.viewModel.UserViewModelFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val coreModules = module {
    //ça nous permet de créer une instance de userRepository
    //on rajoute get() pour récupérer l'instance de FirebaseAuth
    single { userRepository(get() , get()) }

    //ça nous permet de créer une instance
    // de UserViewModelFactory
    factory { UserViewModelFactory(get() )}

    //ça nous permet de créer une instance de UserViewModelFactory
    //et de l'injecter dans le viewModel
    viewModel { UserViewModel(get() )}

}
package com.example.only_friends.modules

import com.example.only_friends.repository.userRepository
import com.example.only_friends.viewModel.UserViewModel
import com.example.only_friends.viewModel.UserViewModelFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val coreModules = module {
    //inject singleton for user repo
    single { userRepository(get() , get()) }

    //inject UserViewModelFactory
    factory { UserViewModelFactory(get() )}

    //inject UserViewModel
    viewModel { UserViewModel(get() )}

}
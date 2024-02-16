package com.example.only_friends.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.only_friends.repository.userRepository
import com.google.firebase.auth.FirebaseAuth


/**
 * UserViewModelFactory is a factory class for creating instances of UserViewModel.
 * It implements the ViewModelProvider.Factory interface.
 *
 * This factory is necessary because UserViewModel requires a userRepository parameter in its constructor.
 * By default, ViewModelProvider can only create ViewModels with no-argument constructors.
 * Therefore, we need to provide a custom factory that knows how to create UserViewModel.
 *
 * The factory takes a userRepository as a parameter in its constructor, and uses it to create UserViewModel.
 *
 * In the create method, it checks if the requested ViewModel class is UserViewModel.
 * If it is, it creates and returns a new instance of UserViewModel using the userRepository.
 * If the requested ViewModel class is not UserViewModel, it throws an IllegalArgumentException.
 */
class UserViewModelFactory(private val repository: userRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
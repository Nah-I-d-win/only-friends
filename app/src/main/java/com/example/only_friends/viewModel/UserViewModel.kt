package com.example.only_friends.viewModel

import androidx.lifecycle.ViewModel
import com.example.only_friends.model.User
import com.example.only_friends.repository.userRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserViewModel(private val repository: userRepository) : ViewModel() {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String) = repository.signUp(email, password)

    fun login(email: String, password: String) = repository.login(email, password)

   // suspend fun insertUser(user: User) = repository.insertUser(user)

    fun insertUser(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }
    fun getUserByEmail(email: String) = repository.getUserByEmail(email)

    fun getUser(uid: String) = repository.getUser(uid)
}
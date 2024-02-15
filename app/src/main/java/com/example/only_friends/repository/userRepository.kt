package com.example.only_friends.repository

import com.example.only_friends.dao.UserDao
import com.example.only_friends.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// withContext
//Dispatchers.IO: pour les operations d'entree/sortie


class userRepository(private val userDao: UserDao) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String) = firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun login(email: String, password: String) = firebaseAuth.signInWithEmailAndPassword(email, password)

    //suspend fun insertUser(user: User) = userDao.insert(user)

    //pour creer l'utilisateur sans attendre  trop longtemps la reponse de la base de donnees
    suspend fun insertUser(user: User) = withContext(Dispatchers.IO) {
        userDao.insert(user)
    }

    fun getUser(uid: String) = userDao.getUser(uid)
}
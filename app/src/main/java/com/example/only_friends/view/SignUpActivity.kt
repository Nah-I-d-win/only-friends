package com.example.only_friends.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.only_friends.MainActivity
import com.example.only_friends.R
import com.example.only_friends.dao.UserDao
import com.example.only_friends.database.AppDatabase
import com.example.only_friends.model.User
import com.example.only_friends.viewModel.UserViewModel
import com.example.only_friends.viewModel.UserViewModelFactory
import com.example.only_friends.repository.userRepository
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import sha256


class SignUpActivity : BaseActivity() {
    private val repository: userRepository by inject()
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            // Naviguer vers la page d'authentification
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        //val userDao = AppDatabase.getDatabase(this).userDao()
        //val repository = userRepository(userDao)
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        val signUpButton = findViewById<Button>(R.id.sign_up_button)
        signUpButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.email).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()
            val passwordHash = findViewById<EditText>(R.id.password).text.toString().sha256()
            viewModel.signUp(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = repository.getCurrentUser()
                    val user = User(firebaseUser!!.uid, email, passwordHash)
                    lifecycleScope.launch {
                        viewModel.insertUser(user)
                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    // pour les messages d'erreurs et la mannière de les afficher

                }
            }
        }
    }
}
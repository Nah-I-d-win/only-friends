package com.example.only_friends.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.only_friends.MainActivity
import com.example.only_friends.R
import com.example.only_friends.database.AppDatabase
import com.example.only_friends.repository.userRepository
import com.example.only_friends.viewModel.UserViewModel
import com.example.only_friends.viewModel.UserViewModelFactory

class LoginActivity : BaseActivity() {
    private lateinit var viewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userDao = AppDatabase.getDatabase(this).userDao()
        val repository = userRepository(userDao)
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            viewModel.login(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // pour les messages
                }

            }
        }
    }
}
package com.example.only_friends.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

class SignUpActivity : BaseActivity() {
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val userDao = AppDatabase.getDatabase(this).userDao()
        val repository = userRepository(userDao)
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        val signUpButton = findViewById<Button>(R.id.sign_up_button)
        signUpButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.email).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()
            viewModel.signUp(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = viewModel.firebaseAuth.currentUser
                    val user = User(firebaseUser!!.uid, "name", email)
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
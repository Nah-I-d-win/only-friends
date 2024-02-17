package com.example.only_friends.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.only_friends.MainActivity
import com.example.only_friends.R
import com.example.only_friends.database.AppDatabase
import com.example.only_friends.repository.userRepository
import com.example.only_friends.viewModel.UserViewModel
import com.example.only_friends.viewModel.UserViewModelFactory
import org.koin.android.ext.android.inject
import sha256

class LoginActivity : BaseActivity() {
    private val repository: userRepository by inject()
    private lateinit var viewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val createAccountTextView = findViewById<TextView>(R.id.create_account)
        createAccountTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

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

        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val errorMessageTextView = findViewById<TextView>(R.id.error_message)


            if (email.isEmpty() || password.isEmpty()) {
                errorMessageTextView.text = "Veuillez remplir tous les champs."
                return@setOnClickListener
            }
            if (isConnectedToInternet()){
                viewModel.login(email, password)
                    .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                       //recuperer le message d'erreur
                        val message = task.exception?.message
                        AlertDialog.Builder(this)
                            .setTitle("Erreur")
                            .setMessage(message)
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }

                }
            } else {
                val passwordHash = passwordEditText.text.toString().sha256()

                viewModel.getUserByEmail(email).observe(this, { user ->
                    if (user != null && user.password == passwordHash) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        AlertDialog.Builder(this)
                            .setTitle("Erreur")
                            .setMessage("Mot de passe ou email incorrect.")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()

                    }
                })
            }
        }
    }

    fun isConnectedToInternet(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

}
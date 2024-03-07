package com.example.only_friends.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.only_friends.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var nameView: TextView
    private lateinit var lastNameView: TextView
    private lateinit var emailView: TextView
    private lateinit var ageView: TextView
    private lateinit var topEmailView: TextView
    private lateinit var topNameView: TextView
    private lateinit var phoneNumberView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        val darkModeButton: Button = findViewById(R.id.dark_mode_button)
        darkModeButton.setOnClickListener {
            toggleDarkMode()
        }


        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        emailView = findViewById(R.id.top_email)
        nameView = findViewById(R.id.ShowNameView)
        lastNameView = findViewById(R.id.showLastnameView)
        topEmailView = findViewById(R.id.top_email)
        emailView = findViewById(R.id.showMailView)
        ageView = findViewById(R.id.oldMailView)
        topNameView = findViewById(R.id.top_name)
        phoneNumberView = findViewById(R.id.ShowPhoneNumberView)

        val editProfileButton = findViewById<Button>(R.id.button_edit_profile)
        editProfileButton.setOnClickListener {
            if (isConnectedToInternet()) {
                val intent = Intent(this, EditProfileActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Une connexion Internet est requise pour éditer le profil.", Toast.LENGTH_SHORT).show()
            }
        }
        val returnHomeButton = findViewById<ImageButton>(R.id.return_button_home)

        returnHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)

        }


        loadUserProfile()
    }



    private fun loadUserProfile() {
        val currentUser = auth.currentUser
        currentUser?.let {
            val email = it.email

            topEmailView.text = email ?: "E-mail non disponible"
            emailView.text = email ?: "E-mail non disponible"

            val userId = it.uid
            db.collection("users").document(userId).get().addOnSuccessListener { document ->
                if (document != null) {
                    nameView.text = document.getString("name")
                    topNameView.text = document.getString("name")
                    lastNameView.text = document.getString("lastName")
                    ageView.text = document.getLong("age")?.toString() ?: "Non spécifié"
                    phoneNumberView.text = document.getString("phoneNumber") ?: "Non spécifié"
                } else {
                }
            }.addOnFailureListener { exception ->
            }
        }
    }

    fun isConnectedToInternet(): Boolean {
        //recuperer le service de connectivité  pour verifier la connexion
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //la class Build.VERSION_CODES contient les versions de l'API Android
        //version_code M est la version Marshmallow
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when{
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

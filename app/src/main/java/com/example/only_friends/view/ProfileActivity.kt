package com.example.only_friends.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.only_friends.MainActivity
import com.example.only_friends.R
import com.example.only_friends.view.BaseActivity
import com.example.only_friends.view.EditProfileActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)


        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        emailView = findViewById(R.id.top_email)
        nameView = findViewById(R.id.ShowNameView)
        lastNameView = findViewById(R.id.showLastnameView)
        topEmailView = findViewById(R.id.top_email)
        emailView = findViewById(R.id.showMailView)
        ageView = findViewById(R.id.oldMailView)
        topNameView = findViewById(R.id.top_name)

        val editProfileButton = findViewById<Button>(R.id.button_edit_profile)
        editProfileButton.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
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
                } else {
                }
            }.addOnFailureListener { exception ->
            }
        }
    }




}

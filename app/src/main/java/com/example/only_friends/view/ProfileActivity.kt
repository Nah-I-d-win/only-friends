package com.example.only_friends.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.only_friends.R
import com.example.only_friends.view.BaseActivity
import com.example.only_friends.view.EditProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    // Définissez les références aux TextViews
    private lateinit var nameView: TextView
    private lateinit var lastNameView: TextView
    private lateinit var emailView: TextView
    private lateinit var ageView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Initialisation des TextViews
        nameView = findViewById(R.id.ShowNameView)
        lastNameView = findViewById(R.id.showLastnameView)
        emailView = findViewById(R.id.showMailView)
        ageView = findViewById(R.id.oldMailView) // Assurez-vous que cet ID est correct pour l'âge

        val editProfileButton = findViewById<Button>(R.id.button_edit_profile)
        editProfileButton.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        loadUserProfile()
    }

    private fun loadUserProfile() {
        val user = auth.currentUser
        user?.let {
            val userId = it.uid
            db.collection("users").document(userId).get().addOnSuccessListener { document ->
                if (document != null) {
                    // Mettez à jour vos vues ici avec les données de l'utilisateur
                    nameView.text = document.getString("name")
                    lastNameView.text = document.getString("lastName")
                    emailView.text = document.getString("email")
                    ageView.text = document.getLong("age")?.toString() ?: "Non spécifié" // Convertissez l'âge en String, gérez les cas où l'âge n'est pas spécifié
                } else {
                    // Gérez le cas où l'utilisateur n'est pas trouvé dans la base de données
                }
            }.addOnFailureListener { exception ->
                // Gérez les erreurs ici
            }
        }
    }
}

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
    private lateinit var topEmailView: TextView
    private lateinit var topNameView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Initialisation des TextViews
        emailView = findViewById(R.id.top_email)
        nameView = findViewById(R.id.ShowNameView)
        lastNameView = findViewById(R.id.showLastnameView)
        topEmailView = findViewById(R.id.top_email) // Initialisation de topEmailView
        emailView = findViewById(R.id.showMailView)
        ageView = findViewById(R.id.oldMailView)
        topNameView = findViewById(R.id.top_name)

        val editProfileButton = findViewById<Button>(R.id.button_edit_profile)
        editProfileButton.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        loadUserProfile()
    }

    private fun loadUserProfile() {
        val currentUser = auth.currentUser
        currentUser?.let {
            // Récupération de l'adresse e-mail de l'utilisateur actuel
            val email = it.email

            // Affichage de l'adresse e-mail dans les TextViews top_email et showMailView
            topEmailView.text = email ?: "E-mail non disponible"
            emailView.text = email ?: "E-mail non disponible" // Assurez-vous que emailView est bien la référence à showMailView

            // Récupération et affichage des autres informations depuis Firestore
            val userId = it.uid
            db.collection("users").document(userId).get().addOnSuccessListener { document ->
                if (document != null) {
                    nameView.text = document.getString("name")
                    topNameView.text = document.getString("name")
                    lastNameView.text = document.getString("lastName")
                    // L'e-mail est déjà défini ci-dessus, pas besoin de le redéfinir ici pour showMailView
                    ageView.text = document.getLong("age")?.toString() ?: "Non spécifié"
                } else {
                    // Gérez le cas où les détails de l'utilisateur ne sont pas trouvés dans Firestore
                }
            }.addOnFailureListener { exception ->
                // Gestion des erreurs
            }
        }
    }


}

package com.example.only_friends.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.only_friends.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val nameEditText: EditText = findViewById(R.id.editName)
        val lastNameEditText: EditText = findViewById(R.id.editLastName)
        val emailEditText: EditText = findViewById(R.id.editEmail)
        val ageEditText: EditText = findViewById(R.id.editAge)
        val phoneNumberEditText: EditText = findViewById(R.id.editPhoneNumber)
        val saveButton: Button = findViewById(R.id.saveProfileButton)

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val age = ageEditText.text.toString().toIntOrNull()
            val phoneNumber = phoneNumberEditText.text.toString().trim()

            if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || age == null) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            updateUserProfile(name, lastName, email, age, phoneNumber)
        }
    }

    private fun updateUserProfile(name: String, lastName: String, email: String, age: Int, phoneNumber: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userMap = hashMapOf(
                "name" to name,
                "lastName" to lastName,
                "email" to email,
                "age" to age,
                "phoneNumber" to phoneNumber
            )

            db.collection("users").document(userId).set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                    finish() // Optionally close the activity
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to Update Profile: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}

package com.example.only_friends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.only_friends.fragments.CollectionFragment
import com.example.only_friends.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val repo = GiftRepository()
        repo.updateData {
            // What to do once the data is loaded
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, HomeFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }


    }
}
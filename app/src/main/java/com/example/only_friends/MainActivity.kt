package com.example.only_friends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.only_friends.fragments.AddGiftFragment
import com.example.only_friends.fragments.CollectionFragment
import com.example.only_friends.fragments.HomeFragment
import com.example.only_friends.view.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(), R.string.home_page_title)

        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.home_page -> {
                    loadFragment(HomeFragment(), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }R.id.collection_page -> {
                    loadFragment(CollectionFragment(), R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }R.id.add_gift_page -> {
                    loadFragment(AddGiftFragment(), R.string.add_gift_page_title)
                    return@setOnNavigationItemSelectedListener true
                } R.id.profile_page -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            }
                else -> false
            }
        }
    }

    fun loadHomeFragment() {
        loadFragment(HomeFragment(), R.string.home_page_title)
    }

    private fun loadFragment(fragment: Fragment, string: Int) {
        val repo = GiftRepository()
        findViewById<TextView>(R.id.page_title).text = resources.getString(string)
        repo.updateData {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}
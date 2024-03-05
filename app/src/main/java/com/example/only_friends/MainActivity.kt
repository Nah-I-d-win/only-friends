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
import com.example.only_friends.view.SettingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this), R.string.home_page_title)

        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }R.id.collection_page -> {
                loadFragment(CollectionFragment(this), R.string.collection_page_title)
                return@setOnNavigationItemSelectedListener true
            }R.id.add_gift_page -> {
                loadFragment(AddGiftFragment(this), R.string.add_gift_page_title)
                return@setOnNavigationItemSelectedListener true
            }
                R.id.settings_page -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

    fun loadHomeFragment() {
        loadFragment(HomeFragment(this), R.string.home_page_title)
    }

    private fun loadFragment(fragment: Fragment, string: Int) {
        val repo = GiftRepository()
        findViewById<TextView>(R.id.page_title).text = resources.getString(string)
        repo.updateData {
            // What to do once the data is loaded
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}
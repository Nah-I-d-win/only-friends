package com.example.only_friends.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.only_friends.repository.GiftRepository
import com.example.only_friends.R
import com.example.only_friends.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(), R.string.home_page_title)


    }

    fun loadHomeFragment() {
        loadFragment(HomeFragment(), R.string.home_page_title)
    }

     fun loadFragment(fragment: Fragment, string: Int) {
       if(!isFinishing && !isDestroyed) {
           val repo = GiftRepository()
           findViewById<TextView>(R.id.page_title).text = resources.getString(string)
           repo.updateData {
               val transaction = supportFragmentManager.beginTransaction()
               transaction.replace(R.id.fragment_container, fragment)
               transaction.addToBackStack(null)
               transaction.commitAllowingStateLoss()//j'ai changer commit par commitAllowingStateLoss
               //pour permettre à la transaction d'être exécutée même après un onSaveInstanceState()
           }
       }
    }
}
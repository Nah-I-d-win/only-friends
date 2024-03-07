package com.example.only_friends.utils

import android.content.Intent
import com.example.only_friends.view.MainActivity
import com.example.only_friends.R
import com.example.only_friends.fragments.AddGiftFragment
import com.example.only_friends.fragments.CollectionFragment
import com.example.only_friends.fragments.HomeFragment
import com.example.only_friends.view.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

object NavigationUtils {
    //cette fonction permet de gérer la navigation entre les fragments
    //elle prend en paramètre la vue de la navigation, l'activité et l'item sélectionné
    //elle retourne un booléen qui indique si la navigation a été effectuée
    fun setupNavigation(navigationView: BottomNavigationView?, activity: MainActivity, selectedItemId: Int) {
        navigationView?.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.home_page -> {
                    if (activity.supportFragmentManager.findFragmentById(R.id.fragment_container) !is HomeFragment) {
                        activity.loadFragment(HomeFragment(), R.string.home_page_title)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page -> {
                    if (activity.supportFragmentManager.findFragmentById(R.id.fragment_container) !is CollectionFragment) {
                        activity.loadFragment(CollectionFragment(), R.string.collection_page_title)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_gift_page -> {
                    if (activity.supportFragmentManager.findFragmentById(R.id.fragment_container) !is AddGiftFragment) {
                        activity.loadFragment(AddGiftFragment(), R.string.add_gift_page_title)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile_page -> {
                    val intent = Intent(activity, ProfileActivity::class.java)
                    activity.startActivity(intent)
                    true
                }
                else -> false
            }
        }
        navigationView?.setSelectedItemId(selectedItemId)
    }
}
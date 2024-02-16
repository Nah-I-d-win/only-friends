package com.example.only_friends.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.only_friends.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BaseActivity : AppCompatActivity() {
    private val PREFS_NAME = "user_prefs"
    private val DARK_MODE = "dark_mode"



    override fun onResume() {
        super.onResume()
        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean(DARK_MODE, false)
        AppCompatDelegate.setDefaultNightMode(if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun toggleDarkMode() {
        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean(DARK_MODE, false)
        prefs.edit().putBoolean(DARK_MODE, !isDarkMode).apply()
        recreate()
    }



}
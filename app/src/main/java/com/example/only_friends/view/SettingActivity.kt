package com.example.only_friends.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.only_friends.R
import com.example.only_friends.fragments.HomeFragment

class SettingActivity : BaseActivity() {
    private var isUserChange = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //add dark mode witch switch
        val darkModeSwitch = findViewById<androidx.appcompat.widget.SwitchCompat>(R.id.dark_mode)
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isUserChange) {
                toggleDarkMode()
            }
            isUserChange = true
        }





    }
}
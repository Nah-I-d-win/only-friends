package com.example.only_friends.view

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.only_friends.R

class SplashActivity : BaseActivity() {
    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val imageView: ImageView = findViewById(R.id.image)
        Glide.with(this).load(R.drawable.loading_2).into(imageView)

        mediaPlayer = MediaPlayer.create(this, R.raw.motor_2)
        mediaPlayer.start()


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, AuthActivity::class.java))
            mediaPlayer?.stop()
            finish()
        }, 3000) // 3 secondes de délai
    }
}
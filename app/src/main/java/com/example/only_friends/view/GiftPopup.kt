package com.example.only_friends.view

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.only_friends.R
import com.example.only_friends.adapter.GiftAdapter
import com.example.only_friends.model.GiftModel
import com.example.only_friends.repository.GiftRepository

class GiftPopup(
    private val adapter: GiftAdapter,
    private val currentGift: GiftModel
): Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_gift_details)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupStarButton()
    }

    private fun setupComponents() {
        val GiftImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentGift.imageUrl)).into(GiftImage)

        findViewById<TextView>(R.id.popup_gift_name).text = currentGift.name
        findViewById<TextView>(R.id.popup_gift_description_subtitle).text = currentGift.description
        findViewById<TextView>(R.id.popup_gift_price_subtitle).text = currentGift.price.toString() + " €"
        findViewById<TextView>(R.id.popup_gift_other_subtitle).text = currentGift.other
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_btn).setOnClickListener{
            dismiss()
        }
    }

    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener{
            val repo = GiftRepository()
            repo.deleteGift(currentGift)
            dismiss()
        }
    }

    private fun setupStarButton() {
        val starButton = findViewById<ImageView>(R.id.star_button)

        updateStar(starButton)

        starButton.setOnClickListener{
            currentGift.liked = !currentGift.liked
            val repo = GiftRepository()
            repo.udpateGift(currentGift)
            updateStar(starButton)
        }
    }

    private fun updateStar(button: ImageView) {
        if (currentGift.liked) {
            button.setImageResource(R.drawable.ic_star)
        }else{
            button.setImageResource(R.drawable.ic_unstar)
        }
    }
}
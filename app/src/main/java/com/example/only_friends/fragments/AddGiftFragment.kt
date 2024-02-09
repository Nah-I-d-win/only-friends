package com.example.only_friends.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.only_friends.GiftModel
import com.example.only_friends.GiftRepository
import com.example.only_friends.MainActivity
import com.example.only_friends.R
import java.util.UUID

class AddGiftFragment(
    private val context: MainActivity
): Fragment() {

    private var uploadedImage: ImageView? = null
    private var file: Uri? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_gift, container, false)

        uploadedImage = view?.findViewById(R.id.preview_image)

        val pickupImageButton = view?.findViewById<Button>(R.id.upload_button)
        pickupImageButton?.setOnClickListener{ pickupImage() }

        val confirmButton = view?.findViewById<Button>(R.id.create_gift_create_button)
        confirmButton?.setOnClickListener { sendForm(view) }

        return view
    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
    }

    private fun sendForm(view: View) {
        val repo = GiftRepository()
        repo.uploadImage(file!!) {
            val giftName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val giftDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val giftPrice = view.findViewById<EditText>(R.id.price_input).text.toString()
            val giftOther = view.findViewById<EditText>(R.id.other_input).text.toString()
            val downloadImageUrl = GiftRepository.Singleton.downloadUri

            val gift = GiftModel(
                UUID.randomUUID().toString(),
                giftName,
                giftDescription,
                downloadImageUrl.toString(),
                false,
                giftPrice.toInt(),
                giftOther,
            )

            repo.insertGift(gift)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 47 && resultCode == Activity.RESULT_OK){
            if (data == null || data.data == null){
                return
            }

            file = data.data
            uploadedImage?.setImageURI(file)
        }
    }

}
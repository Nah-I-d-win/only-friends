package com.example.only_friends.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.only_friends.model.GiftModel
import com.example.only_friends.view.GiftPopup
import com.example.only_friends.repository.GiftRepository
import com.example.only_friends.view.MainActivity
import com.example.only_friends.R

class GiftAdapter(
    val context: MainActivity,
    private val giftList: List<GiftModel>,
    private val layoutId: Int
): RecyclerView.Adapter<GiftAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val giftImage = view.findViewById<ImageView>(R.id.image_item)
        val giftName = view.findViewById<TextView>(R.id.name_item)
        val giftDescription = view.findViewById<TextView>(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = giftList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGift = giftList[position]

        val repo = GiftRepository()

        Glide.with(context).load(Uri.parse(currentGift.imageUrl)).into(holder.giftImage)
        holder.giftName?.text = currentGift.name
        holder.giftDescription?.text = currentGift.description

        if(currentGift.liked) {
            holder.starIcon.setImageResource(R.drawable.ic_star)
        }else{
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }

        holder.starIcon.setOnClickListener {
            currentGift.liked = !currentGift.liked
            repo.udpateGift(currentGift)
        }

        holder.itemView.setOnClickListener {
            GiftPopup(this, currentGift).show()
        }

    }

}
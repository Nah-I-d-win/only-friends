package com.example.only_friends.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.only_friends.GiftRepository.Singleton.giftList
import com.example.only_friends.MainActivity
import com.example.only_friends.R
import com.example.only_friends.adapter.GiftAdapter
import com.example.only_friends.adapter.GiftItemDecoration

class CollectionFragment(): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_collection, container, false)
        val context = requireActivity() as MainActivity


        val collectionRecyclerView = view?.findViewById<RecyclerView>(R.id.collection_recycler_list)
        if (collectionRecyclerView != null) {
            collectionRecyclerView.adapter = GiftAdapter(context, giftList.filter { it.liked }, R.layout.item_vertical_gift)
            collectionRecyclerView.layoutManager = LinearLayoutManager(context)
            collectionRecyclerView.addItemDecoration(GiftItemDecoration())
        }


        return view
    }
}
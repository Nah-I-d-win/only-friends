package com.example.only_friends.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.only_friends.repository.GiftRepository.Singleton.giftList
import com.example.only_friends.view.MainActivity
import com.example.only_friends.R
import com.example.only_friends.adapter.GiftAdapter
import com.example.only_friends.adapter.GiftItemDecoration
import com.example.only_friends.utils.NavigationUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class CollectionFragment(): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_collection, container, false)
        val context = requireActivity() as MainActivity

        val navigationView =  view?.findViewById<BottomNavigationView>(R.id.navigation_view)
        NavigationUtils.setupNavigation(navigationView, requireActivity() as MainActivity, R.id.collection_page)


        val collectionRecyclerView = view?.findViewById<RecyclerView>(R.id.collection_recycler_list)
        if (collectionRecyclerView != null) {
            collectionRecyclerView.adapter = GiftAdapter(context, giftList.filter { it.liked }, R.layout.item_vertical_gift)
            collectionRecyclerView.layoutManager = LinearLayoutManager(context)
            collectionRecyclerView.addItemDecoration(GiftItemDecoration())
        }


        return view
    }
}
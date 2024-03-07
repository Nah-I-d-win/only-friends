package com.example.only_friends.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.only_friends.repository.GiftRepository
import com.example.only_friends.view.MainActivity
import com.example.only_friends.R
import com.example.only_friends.adapter.GiftAdapter
import com.example.only_friends.adapter.GiftItemDecoration
import com.example.only_friends.utils.NavigationUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment() : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        val context = requireActivity() as MainActivity


        val navigationView =  view?.findViewById<BottomNavigationView>(R.id.navigation_view)
        NavigationUtils.setupNavigation(navigationView, requireActivity() as MainActivity, R.id.home_page)


        val horizontalRecyclerView = view?.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        if (horizontalRecyclerView != null) {
            horizontalRecyclerView.adapter = GiftAdapter(context, GiftRepository.Singleton.giftList.filter { !it.liked }, R.layout.item_horizontal_gift)
        }

        val verticalRecyclerView = view?.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        if (verticalRecyclerView != null) {
            verticalRecyclerView.adapter = GiftAdapter(context, GiftRepository.Singleton.giftList, R.layout.item_vertical_gift)
            verticalRecyclerView.addItemDecoration(GiftItemDecoration())
        }

        val recyclerView = view?.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val navigationView = activity?.findViewById<BottomNavigationView>(R.id.navigation_view)
                if (dy > 0 && navigationView?.isShown == true) {
                    navigationView.visibility = View.GONE
                } else if (dy < 0) {
                    navigationView?.visibility = View.VISIBLE
                }
            }
        })

        return view
    }



}
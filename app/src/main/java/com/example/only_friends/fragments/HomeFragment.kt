package com.example.only_friends.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.only_friends.R
import com.example.only_friends.adapter.GiftAdapter

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        val horizontalRecyclerView = view?.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        if (horizontalRecyclerView != null) {
            horizontalRecyclerView.adapter = GiftAdapter()
        }
        return view
    }

}
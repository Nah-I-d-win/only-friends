package com.example.only_friends.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.only_friends.GiftModel
import com.example.only_friends.MainActivity
import com.example.only_friends.R
import com.example.only_friends.adapter.GiftAdapter
import com.example.only_friends.adapter.GiftItemDecoration

class HomeFragment(
    private val context: MainActivity
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        val giftList = arrayListOf<GiftModel>()
        giftList.add(GiftModel("Ducati Panigale V4", "26 490€  [o_O]", "https://images.ctfassets.net/x7j9qwvpvr5s/2zYQHBj5OxbGGvtcdiv2EK/a876ddf620f84c92a362522bcdc14a90/Panigale-MY22-Studio-42-Gallery-1920x1080.jpg", false))
        giftList.add(GiftModel("Yamaha R1", "20 499€  (ಥ﹏ಥ)", "https://cdn-sb.mozu.com/27878-44719/cms/files/016ffa83-d61f-4f5b-ada9-1d76b3a72329?_mzcb=_1680803352036&width=1009&quality=70", false))
        giftList.add(GiftModel("KTM 1390 SUPER DUKE R", "?? ???€  ¯\\_(ツ)_/¯", "https://www.cycleworld.com/resizer/vC5qBfzEZFKPLV7BtvnwYjab1fA=/arc-photo-octane/arc3-prod/public/SLIMO4K2J5ACDJMAU4HBSC3HUM.jpg", true))
        giftList.add(GiftModel("Kawasaki Ninja H2R", "52 000€  ( ͡° ͜ʖ ͡°)", "https://cdn.pixabay.com/photo/2021/04/19/05/12/kawasaki-ninja-h2r-6190254_1280.jpg", true))



        val horizontalRecyclerView = view?.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        if (horizontalRecyclerView != null) {
            horizontalRecyclerView.adapter = GiftAdapter(context, giftList, R.layout.item_horizontal_gift)
        }

        val verticalRecyclerView = view?.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        if (verticalRecyclerView != null) {
            verticalRecyclerView.adapter = GiftAdapter(context, giftList, R.layout.item_vertical_gift)
            verticalRecyclerView.addItemDecoration(GiftItemDecoration())
        }

        return view
    }

}
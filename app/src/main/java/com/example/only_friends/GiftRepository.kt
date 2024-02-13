package com.example.only_friends

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class GiftRepository {

    object Singleton {
        val databaseRef = FirebaseDatabase.getInstance("https://only-friends-f0b88-default-rtdb.europe-west1.firebasedatabase.app").getReference("gift")
        val giftList = arrayListOf<GiftModel>()
    }

    fun updateData(callback: () -> Unit) {
        Singleton.databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Delete all gift before adding new one
                Singleton.giftList.clear()
                for (ds in snapshot.children) {
                    val gift = ds.getValue(GiftModel::class.java)
                    if (gift != null) {
                        Singleton.giftList.add(gift)
                    }
                }

                callback()
            }

            override fun onCancelled(error: DatabaseError) {
                println("failed to read value from database.")
            }

        })
    }

    fun udpateGift(gift: GiftModel) {
        Singleton.databaseRef.child(gift.id).setValue(gift)
    }

    fun deleteGift(gift: GiftModel) {
        Singleton.databaseRef.child(gift.id).removeValue()
    }

}
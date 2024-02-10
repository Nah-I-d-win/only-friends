package com.example.only_friends

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.net.URI
import java.util.UUID

class GiftRepository {

    object Singleton {
        val databaseRef = FirebaseDatabase.getInstance("https://tinnder-of-default-rtdb.europe-west1.firebasedatabase.app").getReference("gift")
        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://tinnder-of.appspot.com/images")
        val giftList = arrayListOf<GiftModel>()
        var downloadUri: Uri? = null
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

    fun insertGift(gift: GiftModel) {
        Singleton.databaseRef.child(gift.id).setValue(gift)
    }

    fun uploadImage(file: Uri, callback: () -> Unit) {
        if (file != null){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = Singleton.storageRef.child(fileName)
            val uploadTask = ref.putFile(file)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Singleton.downloadUri = task.result
                    callback()
                }
            }

        }
    }

}
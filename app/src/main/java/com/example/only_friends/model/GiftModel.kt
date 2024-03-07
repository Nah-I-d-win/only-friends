package com.example.only_friends.model

class GiftModel(
    val id: String = "gift0",
    val name: String = "Moto",
    val description: String = "c'est a moi wsh",
    val imageUrl: String = "https://images.pexels.com/photos/13282420/pexels-photo-13282420.jpeg",
    var liked: Boolean = false,
    var price: Int = 0,
    var other: String = "autre"
)
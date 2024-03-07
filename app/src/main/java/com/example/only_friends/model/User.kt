package com.example.only_friends.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "lastname") val lastname: String?,
    @ColumnInfo(name = "age") val age: Int?,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String?,
    @ColumnInfo(name = "password") val password: String
    )
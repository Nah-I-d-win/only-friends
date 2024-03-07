package com.example.only_friends.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.only_friends.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE uid = :uid LIMIT 1")
    fun getUser(uid: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    fun getUserByEmail(email: String): LiveData<User>


    @Query("UPDATE user SET name = :name, lastname = :lastname, age = :age, phoneNumber = :phoneNumber, email = :email WHERE uid = :uid")
    fun update(name: String, lastname: String, age: Int, phoneNumber: String, email: String, uid: String)


}
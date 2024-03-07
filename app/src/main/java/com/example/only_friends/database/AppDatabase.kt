package com.example.only_friends.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.only_friends.dao.UserDao
import com.example.only_friends.model.User

//si modification de la base de donnees, changer la version(augmenter)
@Database(entities = [User::class], version = 4)
abstract class AppDatabase : RoomDatabase() {

    //instance pour pouvoir acceder a la base de donnees
    abstract fun userDao(): UserDao

    //singleton pour eviter d'avoir plusieurs instances de la base de donnees
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()//permet de supprimer la base de donnees si la version change
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
package com.example.ggram.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ggram.database.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1)
abstract class UserDatabase : RoomDatabase()  {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context) : UserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java, "user_db"
                ).build()
            }
    }
}

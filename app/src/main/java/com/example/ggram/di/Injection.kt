package com.example.ggram.di

import android.content.Context
import com.example.ggram.remote.UserRepository
import com.example.ggram.room.UserDatabase

//object Injection {
//    fun providerRepository(context: Context): UserRepository {
//        val database = UserDatabase.getInstance(context)
//        val dao = database.userDao()
//        return UserRepository(dao)
//    }
//}
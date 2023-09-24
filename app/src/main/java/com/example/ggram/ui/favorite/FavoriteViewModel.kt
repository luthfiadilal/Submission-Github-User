package com.example.ggram.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ggram.database.FavoriteUser
import com.example.ggram.remote.UserRepository
import com.example.ggram.room.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository = UserRepository(UserDatabase.getInstance(application).userDao())

    val favoriteUser : LiveData<List<FavoriteUser>> = userRepository.getAllFavoriteUsers()

    fun insertUser(user: FavoriteUser) {
        userRepository.insertUser(user, true)
    }

    fun deleteUser(user: FavoriteUser) {
        userRepository.deleteUser(user, false)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return userRepository.getFavoriteUserByUsername(username)
    }
}
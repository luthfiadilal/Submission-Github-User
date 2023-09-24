package com.example.ggram.remote

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.ggram.database.FavoriteUser
import com.example.ggram.room.UserDao
import com.example.ggram.room.UserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(
    private val mUserDao: UserDao,
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()
) {

    fun insertUser(user: FavoriteUser, favoriteState: Boolean) {
        executorService.execute {
            user.isFavorite = favoriteState
            mUserDao.insertUser(user)
        }
    }

    fun deleteUser(user: FavoriteUser, favoriteState: Boolean) {
        executorService.execute {
            user.isFavorite = favoriteState
            mUserDao.deleteUser(user)
        }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return mUserDao.getFavoriteUserByUsername(username)
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return mUserDao.getAllFavoriteUsers()
    }

}
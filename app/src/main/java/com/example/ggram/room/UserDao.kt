package com.example.ggram.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ggram.database.FavoriteUser

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: FavoriteUser)

    @Update
    fun updateUser(user: FavoriteUser)

    @Delete
    fun deleteUser(user: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>

    @Query("SELECT * FROM FavoriteUser WHERE isFavorite = 1")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>>
}
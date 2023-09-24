package com.example.ggram.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    val username : String = "",

    @ColumnInfo(name = "avatarUrl")
    val avatarUrl : String? = null,

    @ColumnInfo(name = "isFavorite")
    var isFavorite : Boolean
) : Parcelable

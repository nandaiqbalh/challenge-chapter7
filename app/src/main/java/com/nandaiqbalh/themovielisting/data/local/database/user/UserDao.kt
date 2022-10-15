package com.nandaiqbalh.themovielisting.data.local.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nandaiqbalh.themovielisting.data.local.model.user.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun registerUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUser(username: String) : UserEntity
}
package com.nandaiqbalh.themovielisting.data.local.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nandaiqbalh.themovielisting.data.local.model.user.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun registerUser(user: UserEntity): Long

    @Update
    suspend fun updateUser(user: UserEntity): Int

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username = :username)")
    suspend fun getIfUserExists(username: String) : Boolean

    @Query("SELECT * FROM users WHERE userId = :id")
    suspend fun getUserById(id: Long): UserEntity

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): UserEntity

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username = :username AND password = :password)")
    suspend fun validateUserLogin(username: String, password: String) : Boolean
}
package com.nandaiqbalh.themovielisting.data.local.repository

import com.nandaiqbalh.themovielisting.data.local.model.user.UserEntity
import com.nandaiqbalh.themovielisting.data.local.preference.UserPreferenceDataSource
import com.nandaiqbalh.themovielisting.data.local.preference.UserPreferences
import com.nandaiqbalh.themovielisting.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {
    suspend fun setUser(user: UserPreferences)
    suspend fun updateUser(user: UserPreferences)
    suspend fun setUserLogin(isLogin: Boolean)
    suspend fun setProfileImage(image: String)

    fun getUser(): Flow<UserPreferences>
    fun getUserLogin(): Flow<Boolean>
    fun getUserProfileImage(): Flow<String>
}

class UserRepositoryImpl @Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
): UserRepository {
    override suspend fun setUser(user: UserPreferences) {
        userPreferenceDataSource.setUser(user)
    }

    override suspend fun updateUser(user: UserPreferences) {
        userPreferenceDataSource.updateUser(user)
    }

    override suspend fun setUserLogin(isLogin: Boolean) {
        userPreferenceDataSource.setUserLogin(isLogin)
    }

    override suspend fun setProfileImage(image: String) {
        userPreferenceDataSource.setProfileImage(image)
    }

    override fun getUser(): Flow<UserPreferences> {
        return userPreferenceDataSource.getUser()
    }

    override fun getUserLogin(): Flow<Boolean> {
        return userPreferenceDataSource.getUserLogin()
    }

    override fun getUserProfileImage(): Flow<String> {
        return userPreferenceDataSource.getUserProfileImage()
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }
}
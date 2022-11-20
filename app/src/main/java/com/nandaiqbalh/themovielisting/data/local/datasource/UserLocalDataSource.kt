package com.nandaiqbalh.themovielisting.data.local.datasource

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserLocalDataSource {
    suspend fun setUser(user: UserPreferences)

    suspend fun updateUser(user: UserPreferences)

    suspend fun setUserLogin(isLogin: Boolean)

    suspend fun setProfileImage(image: String)

    fun getUser(): Flow<UserPreferences>

    fun getUserLogin(): Flow<Boolean>

    fun getUserProfileImage(): Flow<String>
}

class UserLocalDataSourceImpl @Inject constructor(
    private val userDataStore: UserDataStoreManager
): UserLocalDataSource {
    override suspend fun setUser(user: UserPreferences) {
        userDataStore.setUser(user)
    }

    override suspend fun updateUser(user: UserPreferences) {
        userDataStore.updateUser(user)
    }

    override suspend fun setUserLogin(isLogin: Boolean) {
        userDataStore.setUserLogin(isLogin)
    }

    override suspend fun setProfileImage(image: String) {
        userDataStore.setProfileImage(image)
    }

    override fun getUser(): Flow<UserPreferences> {
        return userDataStore.getUser()
    }

    override fun getUserLogin(): Flow<Boolean> {
        return userDataStore.getUserLogin()
    }

    override fun getUserProfileImage(): Flow<String> {
        return userDataStore.getUserProfileImage()
    }
}
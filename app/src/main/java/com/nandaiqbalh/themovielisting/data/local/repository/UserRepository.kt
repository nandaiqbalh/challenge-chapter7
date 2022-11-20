package com.nandaiqbalh.themovielisting.data.local.repository

import androidx.fragment.app.Fragment
import com.nandaiqbalh.themovielisting.data.local.datasource.UserLocalDataSource
import com.nandaiqbalh.themovielisting.data.local.datasource.UserPreferences
import com.nandaiqbalh.themovielisting.data.network.firebase.datasource.UserRemoteDataSource
import com.nandaiqbalh.themovielisting.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {
    fun createUserFirebase(username: String, email: String, password: String)
    suspend fun signInFirebase(email: String, password: String)
    fun isLoginSuccess(): Boolean
    fun getUserDetail(fragment: Fragment)
    fun updateProfile(fragment: Fragment, userHashMap: HashMap<String, Any>)

    suspend fun setUser(user: UserPreferences)
    suspend fun updateUser(user: UserPreferences)
    suspend fun setUserLogin(isLogin: Boolean)
    suspend fun setProfileImage(image: String)

    fun getUser(): Flow<UserPreferences>
    fun getUserLogin(): Flow<Boolean>
    fun getUserProfileImage(): Flow<String>
}

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {

    override fun createUserFirebase(
        username: String,
        email: String,
        password: String
    ) {
        userRemoteDataSource.createUserFirebase(username, email, password)
    }

    override suspend fun signInFirebase(
        email: String,
        password: String,
    ){
        return userRemoteDataSource.signInFirebase(email, password)
    }

    override fun isLoginSuccess(): Boolean {
        return userRemoteDataSource.isLoginSuccess()
    }

    override fun getUserDetail(fragment: Fragment) {
        userRemoteDataSource.getUserDetail(fragment)
    }

    override fun updateProfile(fragment: Fragment, userHashMap: HashMap<String, Any>) {
        userRemoteDataSource.updateProfile(fragment, userHashMap)
    }

    override suspend fun setUser(user: UserPreferences) {
        userLocalDataSource.setUser(user)
    }

    override suspend fun updateUser(user: UserPreferences) {
        userLocalDataSource.updateUser(user)
    }

    override suspend fun setUserLogin(isLogin: Boolean) {
        userLocalDataSource.setUserLogin(isLogin)
    }

    override suspend fun setProfileImage(image: String) {
        userLocalDataSource.setProfileImage(image)
    }

    override fun getUser(): Flow<UserPreferences> {
        return userLocalDataSource.getUser()
    }

    override fun getUserLogin(): Flow<Boolean> {
        return userLocalDataSource.getUserLogin()
    }

    override fun getUserProfileImage(): Flow<String> {
        TODO("Not yet implemented")
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }
}
package com.nandaiqbalh.themovielisting.data.network.firebase.datasource

import androidx.fragment.app.Fragment
import com.nandaiqbalh.themovielisting.data.network.firebase.authentication.UserAuthManager
import javax.inject.Inject

interface UserRemoteDataSource {
    fun createUserFirebase(username: String, email: String, password: String)
    suspend fun signInFirebase(email: String, password: String)
    fun isLoginSuccess(): Boolean
    fun getUserDetail(fragment: Fragment)
    fun updateProfile(fragment: Fragment, userHashMap: HashMap<String, Any>)

}

class UserRemoteDataSourceImpl @Inject constructor(
    private val userAuthManager: UserAuthManager
): UserRemoteDataSource {
    override fun createUserFirebase(username: String, email: String, password: String) {
        userAuthManager.createUserFirebase(username, email, password)
    }

    override suspend fun signInFirebase(
        email: String,
        password: String,
    ){
        return userAuthManager.signInFirebase(email, password)
    }

    override fun isLoginSuccess(): Boolean {
        return userAuthManager.isLoginSuccess()
    }

    override fun getUserDetail(fragment: Fragment) {
        userAuthManager.getUserDetail(fragment)
    }

    override fun updateProfile(fragment: Fragment, userHashMap: HashMap<String, Any>) {
        userAuthManager.updateProfile(fragment, userHashMap)
    }
}
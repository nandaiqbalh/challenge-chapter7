package com.nandaiqbalh.themovielisting.presentation.ui.user.login

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.nandaiqbalh.themovielisting.data.local.model.user.UserEntity
import com.nandaiqbalh.themovielisting.data.local.datasource.UserPreferences
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepository
import com.nandaiqbalh.themovielisting.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository) : ViewModel(){

    fun signInFirebase(email: String, password: String){
        viewModelScope.launch {
            repository.signInFirebase(email, password)
        }
    }

    fun isLoginSuccess(): Boolean = repository.isLoginSuccess()

    fun getUser(): LiveData<UserPreferences> {
        return repository.getUser().asLiveData()
    }

    fun setUserLogin(isLogin: Boolean) {
        viewModelScope.launch {
            repository.setUserLogin(isLogin)
        }
    }

    fun getUserLogin(): LiveData<Boolean> {
        return repository.getUserLogin().asLiveData()
    }

}
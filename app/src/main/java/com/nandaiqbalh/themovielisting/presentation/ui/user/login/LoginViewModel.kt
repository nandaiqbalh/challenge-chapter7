package com.nandaiqbalh.themovielisting.presentation.ui.user.login

import androidx.lifecycle.*
import com.nandaiqbalh.themovielisting.data.local.model.user.UserEntity
import com.nandaiqbalh.themovielisting.data.local.preference.UserPreferences
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepository
import com.nandaiqbalh.themovielisting.wrapper.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel(){

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
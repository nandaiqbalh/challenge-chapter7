package com.nandaiqbalh.themovielisting.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.themovielisting.data.local.model.user.UserEntity
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepository
import com.nandaiqbalh.themovielisting.wrapper.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel(){

    private var _getUserResult = MutableLiveData<Resource<UserEntity>>()
    val getUserResult: LiveData<Resource<UserEntity>> get() = _getUserResult

    fun getUser(username: String) {
        viewModelScope.launch {
            _getUserResult.postValue(repository.getUser(username))
        }
    }

    fun checkIfUserLoggedIn(): Boolean {
        return repository.checkIfUserLoggedIn()
    }

    fun setIfUserLogin(userLoggedIn: Boolean){
        return repository.setIfUserLogin(userLoggedIn)
    }
}
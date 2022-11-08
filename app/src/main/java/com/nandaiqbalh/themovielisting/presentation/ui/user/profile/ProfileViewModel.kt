package com.nandaiqbalh.themovielisting.presentation.ui.user.profile

import androidx.lifecycle.*
import com.nandaiqbalh.themovielisting.data.local.preference.UserPreferences
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository): ViewModel() {

    fun getUser(): LiveData<UserPreferences> {
        return repository.getUser().asLiveData()
    }

    fun setUserLogin(isLogin: Boolean) {
        viewModelScope.launch {
            repository.setUserLogin(isLogin)
        }
    }

    fun updateUser(user: UserPreferences) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }

    fun setProfileImage(image: String) {
        viewModelScope.launch {
            repository.setProfileImage(image)
        }
    }
}
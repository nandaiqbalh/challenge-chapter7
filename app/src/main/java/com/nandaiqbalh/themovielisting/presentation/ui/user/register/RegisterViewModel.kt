package com.nandaiqbalh.themovielisting.presentation.ui.user.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.themovielisting.data.local.model.user.UserEntity
import com.nandaiqbalh.themovielisting.data.local.preference.UserPreferences
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepository
import com.nandaiqbalh.themovielisting.util.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    fun registerUser(id: Int, username: String, email: String, password: String) {
        viewModelScope.launch {
            repository.setUser(id, username, email, password)
        }
    }

    fun getUser(): LiveData<UserPreferences> {
        return repository.getUser().asLiveData()
    }
}
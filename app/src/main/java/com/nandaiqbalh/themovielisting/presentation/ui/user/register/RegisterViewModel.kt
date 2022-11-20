package com.nandaiqbalh.themovielisting.presentation.ui.user.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.themovielisting.data.local.model.user.UserEntity
import com.nandaiqbalh.themovielisting.data.local.datasource.UserPreferences
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepository
import com.nandaiqbalh.themovielisting.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    fun createUserFirebase(username: String, email: String, password: String) {
        viewModelScope.launch {
            repository.createUserFirebase(username, email, password)
        }
    }

    fun registerUser(user: UserPreferences) {
        viewModelScope.launch {
            repository.setUser(user)
        }
    }

    fun getUser(): LiveData<UserPreferences> {
        return repository.getUser().asLiveData()
    }
}
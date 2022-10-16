package com.nandaiqbalh.themovielisting.presentation.ui.user.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.themovielisting.data.local.model.user.UserEntity
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepository
import com.nandaiqbalh.themovielisting.util.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    private var _getIfUserExistResult = SingleLiveEvent<Boolean>()
    val getIfUserExistResult: SingleLiveEvent<Boolean> get() = _getIfUserExistResult

    fun registerUser(user: UserEntity) {
        viewModelScope.launch {
            repository.registerUser(user)
        }
    }

    fun getIfUserExist(username: String){
        viewModelScope.launch {
            _getIfUserExistResult.value = repository.getIfUserExists(username)
        }
    }
}

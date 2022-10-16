package com.nandaiqbalh.themovielisting.presentation.ui.movie.home

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.themovielisting.data.local.model.user.UserEntity
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepository
import com.nandaiqbalh.themovielisting.data.network.model.popular.Popular
import com.nandaiqbalh.themovielisting.data.network.model.toprated.TopRated
import com.nandaiqbalh.themovielisting.data.network.repository.MovieRepository
import com.nandaiqbalh.themovielisting.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val movieRepository: MovieRepository, private val userRepository: UserRepository): ViewModel() {

    private val _getPopularResult = MutableLiveData<Resource<Popular>>()
    val getPopularResult: LiveData<Resource<Popular>> get() = _getPopularResult

    private val _getTopRatedResult = MutableLiveData<Resource<TopRated>>()
    val getTopRatedResult: LiveData<Resource<TopRated>> get() = _getTopRatedResult

    private val _userByIdResult = MutableLiveData<UserEntity>()
    val userByIdResult: LiveData<UserEntity> get() = _userByIdResult

    fun getPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = movieRepository.getPopular()
            viewModelScope.launch(Dispatchers.Main) {
                _getPopularResult.postValue(data)
            }
        }
    }

    fun getTopRated() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = movieRepository.getTopRated()
            viewModelScope.launch(Dispatchers.Main) {
                _getTopRatedResult.postValue(data)
            }
        }
    }
    fun getUserId(): Long {
        return userRepository.getUserId()
    }

    fun getUserById(id: Long) {
        viewModelScope.launch {
            _userByIdResult.postValue(userRepository.getUserById(id))
        }
    }

    var listStateParcel: Parcelable? = null

    fun saveListState(parcel: Parcelable) {
        listStateParcel = parcel
    }
}
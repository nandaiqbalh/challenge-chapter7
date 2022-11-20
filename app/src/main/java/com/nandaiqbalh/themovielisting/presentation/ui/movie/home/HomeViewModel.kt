package com.nandaiqbalh.themovielisting.presentation.ui.movie.home

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.nandaiqbalh.themovielisting.data.local.model.user.UserEntity
import com.nandaiqbalh.themovielisting.data.local.datasource.UserPreferences
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepository
import com.nandaiqbalh.themovielisting.data.network.movie.model.popular.Popular
import com.nandaiqbalh.themovielisting.data.network.movie.model.toprated.TopRated
import com.nandaiqbalh.themovielisting.data.network.movie.repository.MovieRepository
import com.nandaiqbalh.themovielisting.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository, private val userRepository: UserRepository): ViewModel() {

    private val _getPopularResult = MutableLiveData<Resource<Popular>>()
    val getPopularResult: LiveData<Resource<Popular>> get() = _getPopularResult

    private val _getTopRatedResult = MutableLiveData<Resource<TopRated>>()
    val getTopRatedResult: LiveData<Resource<TopRated>> get() = _getTopRatedResult

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

    fun getUserDetail(fragment: Fragment) {
        userRepository.getUserDetail(fragment)
    }

    fun getUser(): LiveData<UserPreferences> {
        return userRepository.getUser().asLiveData()
    }

    var listStateParcel: Parcelable? = null

    fun saveListState(parcel: Parcelable) {
        listStateParcel = parcel
    }
}
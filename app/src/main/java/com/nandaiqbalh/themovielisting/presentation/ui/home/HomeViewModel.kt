package com.nandaiqbalh.themovielisting.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.themovielisting.data.network.model.popular.Popular
import com.nandaiqbalh.themovielisting.data.network.model.toprated.TopRated
import com.nandaiqbalh.themovielisting.data.network.repository.MovieRepository
import com.nandaiqbalh.themovielisting.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository): ViewModel() {

    private val _getPopularResult = MutableLiveData<Resource<Popular>>()
    val getPopularResult: LiveData<Resource<Popular>> get() = _getPopularResult

    private val _getTopRatedResult = MutableLiveData<Resource<TopRated>>()
    val getTopRatedResult: LiveData<Resource<TopRated>> get() = _getTopRatedResult

    fun getPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getPopular()
            viewModelScope.launch(Dispatchers.Main) {
                _getPopularResult.postValue(data)
            }
        }
    }

    fun getTopRated() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getTopRated()
            viewModelScope.launch(Dispatchers.Main) {
                _getTopRatedResult.postValue(data)
            }
        }
    }
}
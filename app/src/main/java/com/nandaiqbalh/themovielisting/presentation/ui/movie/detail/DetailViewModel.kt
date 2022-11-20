package com.nandaiqbalh.themovielisting.presentation.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.themovielisting.data.network.movie.model.detail.DetailMovie
import com.nandaiqbalh.themovielisting.data.network.movie.repository.MovieRepository
import com.nandaiqbalh.themovielisting.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: MovieRepository): ViewModel() {

    private val _detailResult = MutableLiveData<Resource<DetailMovie>>()
    val detailResult: LiveData<Resource<DetailMovie>> get() = _detailResult

    fun getDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getDetail(id)
            viewModelScope.launch(Dispatchers.Main) {
                _detailResult.postValue(data)
            }
        }
    }
}
package com.nandaiqbalh.themovielisting.data.network.movie.datasource

import com.nandaiqbalh.themovielisting.data.network.movie.model.detail.DetailMovie
import com.nandaiqbalh.themovielisting.data.network.movie.model.popular.Popular
import com.nandaiqbalh.themovielisting.data.network.movie.model.toprated.TopRated
import com.nandaiqbalh.themovielisting.data.network.service.MovieApiService
import javax.inject.Inject

interface MovieDataSource {
    suspend fun getPopular(): Popular
    suspend fun getTopRated(): TopRated
    suspend fun getDetail(id: Int): DetailMovie
}

class MovieDataSourceImpl @Inject constructor(private val apiService: MovieApiService):
    MovieDataSource {
    override suspend fun getPopular(): Popular {
        return apiService.getPopular()
    }

    override suspend fun getTopRated(): TopRated {
        return apiService.getTopRated()
    }

    override suspend fun getDetail(id: Int): DetailMovie {
        return apiService.getDetail(id)
    }
}
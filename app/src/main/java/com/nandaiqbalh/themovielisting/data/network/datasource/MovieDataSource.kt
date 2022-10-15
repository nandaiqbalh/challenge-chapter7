package com.nandaiqbalh.themovielisting.data.network.datasource

import com.nandaiqbalh.themovielisting.data.network.model.popular.Popular
import com.nandaiqbalh.themovielisting.data.network.model.toprated.TopRated
import com.nandaiqbalh.themovielisting.data.network.service.MovieApiService

interface MovieDataSource {
    suspend fun getPopular(): Popular
    suspend fun getTopRated(): TopRated
}

class MovieDataSourceImpl(private val apiService: MovieApiService): MovieDataSource {
    override suspend fun getPopular(): Popular {
        return apiService.getPopular()
    }

    override suspend fun getTopRated(): TopRated {
        return apiService.getTopRated()
    }
}
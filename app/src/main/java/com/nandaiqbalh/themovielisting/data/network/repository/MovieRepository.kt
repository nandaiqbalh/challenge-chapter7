package com.nandaiqbalh.themovielisting.data.network.repository

import com.nandaiqbalh.themovielisting.data.network.datasource.MovieDataSource
import com.nandaiqbalh.themovielisting.data.network.model.popular.Popular
import com.nandaiqbalh.themovielisting.data.network.model.toprated.TopRated
import com.nandaiqbalh.themovielisting.wrapper.Resource

interface MovieRepository {
    suspend fun getPopular(): Resource<Popular>
    suspend fun getTopRated(): Resource<TopRated>
}

class MovieRepositoryImpl(private val dataSource: MovieDataSource): MovieRepository {
    override suspend fun getPopular(): Resource<Popular> {
        return proceed {
            dataSource.getPopular()
        }
    }

    override suspend fun getTopRated(): Resource<TopRated> {
        return proceed {
            dataSource.getTopRated()
        }
    }

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
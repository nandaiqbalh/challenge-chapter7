package com.nandaiqbalh.themovielisting.data.network.movie.repository

import com.nandaiqbalh.themovielisting.data.network.movie.datasource.MovieDataSource
import com.nandaiqbalh.themovielisting.data.network.movie.model.detail.DetailMovie
import com.nandaiqbalh.themovielisting.data.network.movie.model.popular.Popular
import com.nandaiqbalh.themovielisting.data.network.movie.model.toprated.TopRated
import com.nandaiqbalh.themovielisting.wrapper.Resource
import javax.inject.Inject

interface MovieRepository {
    suspend fun getPopular(): Resource<Popular>
    suspend fun getTopRated(): Resource<TopRated>
    suspend fun getDetail(id: Int): Resource<DetailMovie>
}

class MovieRepositoryImpl @Inject constructor(private val dataSource: MovieDataSource):
    MovieRepository {
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

    override suspend fun getDetail(id: Int): Resource<DetailMovie> {
        return proceed {
            dataSource.getDetail(id)
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
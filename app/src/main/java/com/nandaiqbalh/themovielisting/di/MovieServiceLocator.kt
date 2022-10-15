package com.nandaiqbalh.themovielisting.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nandaiqbalh.themovielisting.data.network.datasource.MovieDataSource
import com.nandaiqbalh.themovielisting.data.network.datasource.MovieDataSourceImpl
import com.nandaiqbalh.themovielisting.data.network.repository.MovieRepository
import com.nandaiqbalh.themovielisting.data.network.repository.MovieRepositoryImpl
import com.nandaiqbalh.themovielisting.data.network.service.MovieApiService

object MovieServiceLocator {

    private fun provideChucker(appContext: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(appContext).build()
    }

    private fun provideApiService(chuckerInterceptor: ChuckerInterceptor): MovieApiService {
        return MovieApiService.invoke(chuckerInterceptor)
    }

    private fun provideDataSource(apiService: MovieApiService): MovieDataSource {
        return MovieDataSourceImpl(apiService)
    }

    fun provideMovieRepository(context: Context): MovieRepository {
        return MovieRepositoryImpl(provideDataSource(provideApiService(provideChucker(context))))
    }
}
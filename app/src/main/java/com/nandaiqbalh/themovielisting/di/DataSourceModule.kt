package com.nandaiqbalh.themovielisting.di

import com.nandaiqbalh.themovielisting.data.local.preference.UserPreferenceDataSource
import com.nandaiqbalh.themovielisting.data.local.preference.UserPreferenceDataSourceImpl
import com.nandaiqbalh.themovielisting.data.network.datasource.MovieDataSource
import com.nandaiqbalh.themovielisting.data.network.datasource.MovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideMovieDataSource(movieRemoteDataSourceImpl: MovieDataSourceImpl): MovieDataSource

    @Binds
    abstract fun provideUserDataSource(userPreferenceDataSourceImpl: UserPreferenceDataSourceImpl): UserPreferenceDataSource
}
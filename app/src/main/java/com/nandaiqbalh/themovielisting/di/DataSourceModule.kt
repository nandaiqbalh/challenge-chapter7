package com.nandaiqbalh.themovielisting.di

import com.nandaiqbalh.themovielisting.data.local.datasource.UserLocalDataSource
import com.nandaiqbalh.themovielisting.data.local.datasource.UserLocalDataSourceImpl
import com.nandaiqbalh.themovielisting.data.network.firebase.datasource.UserRemoteDataSource
import com.nandaiqbalh.themovielisting.data.network.firebase.datasource.UserRemoteDataSourceImpl
import com.nandaiqbalh.themovielisting.data.network.movie.datasource.MovieDataSource
import com.nandaiqbalh.themovielisting.data.network.movie.datasource.MovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideMovieRemoteDataSource(movieRemoteDataSourceImpl: MovieDataSourceImpl): MovieDataSource

    @Binds
    abstract fun provideUserLocalDataSource(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    abstract fun provideUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource
}
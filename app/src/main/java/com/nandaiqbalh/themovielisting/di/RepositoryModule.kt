package com.nandaiqbalh.themovielisting.di

import com.nandaiqbalh.themovielisting.data.local.repository.UserRepository
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepositoryImpl
import com.nandaiqbalh.themovielisting.data.network.movie.repository.MovieRepository
import com.nandaiqbalh.themovielisting.data.network.movie.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}
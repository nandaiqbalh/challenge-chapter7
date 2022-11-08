package com.nandaiqbalh.themovielisting.di

import android.content.Context
import com.nandaiqbalh.themovielisting.data.local.database.AppDatabase
import com.nandaiqbalh.themovielisting.data.local.database.user.UserDao
import com.nandaiqbalh.themovielisting.data.local.database.user.UserDataSource
import com.nandaiqbalh.themovielisting.data.local.database.user.UserDataSourceImpl
import com.nandaiqbalh.themovielisting.data.local.preference.UserDataStoreManager
import com.nandaiqbalh.themovielisting.data.local.preference.UserPreference
import com.nandaiqbalh.themovielisting.data.local.preference.UserPreferenceDataSource
import com.nandaiqbalh.themovielisting.data.local.preference.UserPreferenceDataSourceImpl
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepository
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepositoryImpl

object UserServiceLocator {

    private fun provideUserPreference(context: Context): UserDataStoreManager {
        return UserDataStoreManager(context)
    }
    private fun provideUserPreferenceDataSource(context: Context): UserPreferenceDataSource {
        return UserPreferenceDataSourceImpl(provideUserPreference(context))
    }
    private fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
    private fun provideUserDao(context: Context): UserDao {
        return provideAppDatabase(context).userDao()
    }

    fun provideUserRepository(context: Context): UserRepository {
        return UserRepositoryImpl(
            provideUserPreferenceDataSource(context)
        )
    }
}
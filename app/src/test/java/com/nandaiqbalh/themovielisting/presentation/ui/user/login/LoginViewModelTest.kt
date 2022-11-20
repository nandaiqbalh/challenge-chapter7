package com.nandaiqbalh.themovielisting.presentation.ui.user.login

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nandaiqbalh.themovielisting.data.local.datasource.UserDataStoreManager
import com.nandaiqbalh.themovielisting.data.local.datasource.UserLocalDataSource
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepository
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepositoryImpl
import com.nandaiqbalh.themovielisting.data.network.firebase.authentication.UserAuthManager
import com.nandaiqbalh.themovielisting.data.network.firebase.datasource.UserRemoteDataSource
import com.nandaiqbalh.themovielisting.data.network.firebase.datasource.UserRemoteDataSourceImpl
import com.nandaiqbalh.themovielisting.data.network.service.MovieApiService
import com.nandaiqbalh.themovielisting.util.getOrAwaitValue
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var apiService: MovieApiService
    private lateinit var dataStore: UserDataStoreManager
    private lateinit var userAuthManager: UserAuthManager
    private lateinit var localDataSource: UserLocalDataSource
    private lateinit var remoteDataSource: UserRemoteDataSource
    private lateinit var repository: UserRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        val context = ApplicationProvider.getApplicationContext<Context>()

        apiService = mockk()
        dataStore = UserDataStoreManager(context)
        userAuthManager = UserAuthManager(context)
        localDataSource = com.nandaiqbalh.themovielisting.data.local.datasource.UserLocalDataSourceImpl(dataStore)
        remoteDataSource = UserRemoteDataSourceImpl(userAuthManager)
        repository = UserRepositoryImpl(localDataSource, remoteDataSource)
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun `when user logged in`() {
        val status = true

        viewModel.setUserLogin(status)

        val actual = viewModel.getUserLogin().getOrAwaitValue()
        Assert.assertTrue(actual)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
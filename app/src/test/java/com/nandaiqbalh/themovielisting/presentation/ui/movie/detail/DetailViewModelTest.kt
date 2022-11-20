package com.nandaiqbalh.themovielisting.presentation.ui.movie.detail

import android.accounts.NetworkErrorException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nandaiqbalh.themovielisting.data.network.movie.datasource.MovieDataSource
import com.nandaiqbalh.themovielisting.data.network.movie.datasource.MovieDataSourceImpl
import com.nandaiqbalh.themovielisting.data.network.movie.model.detail.DetailMovie
import com.nandaiqbalh.themovielisting.data.network.movie.repository.MovieRepository
import com.nandaiqbalh.themovielisting.data.network.movie.repository.MovieRepositoryImpl
import com.nandaiqbalh.themovielisting.data.network.service.MovieApiService
import com.nandaiqbalh.themovielisting.util.getOrAwaitValue
import com.nandaiqbalh.themovielisting.wrapper.Resource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var apiService: MovieApiService
    private lateinit var dataSource: MovieDataSource
    private lateinit var movieRepository: MovieRepository
    private lateinit var viewModel: DetailViewModel

    private val dummyId = 238

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        apiService = mockk()
        dataSource = MovieDataSourceImpl(apiService)
        movieRepository = MovieRepositoryImpl(dataSource)
        viewModel = DetailViewModel(movieRepository)
    }

    @Test
    fun `when getDetail should not be null and return Success`() {
        val respDetail = mockk<Resource<DetailMovie>>()

        every {
            runBlocking {
                movieRepository.getDetail(dummyId)
            }
        } returns respDetail

        viewModel.getDetail(dummyId)

        verify {
            runBlocking {
                movieRepository.getDetail(dummyId)
            }
        }

        val result = viewModel.detailResult.getOrAwaitValue()
        assertNotNull(result)
        assertTrue(result is Resource.Success)
        assertEquals(respDetail, result.payload)
    }

    @Test
    fun `when getDetail should be null and return Error`() {
        val respDetail = mockk<Resource<DetailMovie>>()

        every {
            runBlocking {
                movieRepository.getDetail(dummyId)
            }
        } returns respDetail

        viewModel.getDetail(dummyId)

        val result = viewModel.detailResult.getOrAwaitValue()
        assertNotNull(result)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
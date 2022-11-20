package com.nandaiqbalh.themovielisting.presentation.ui.movie.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.nandaiqbalh.themovielisting.data.local.repository.UserRepository
import com.nandaiqbalh.themovielisting.data.network.movie.model.popular.Popular
import com.nandaiqbalh.themovielisting.data.network.movie.repository.MovieRepository
import com.nandaiqbalh.themovielisting.util.getOrAwaitValue
import com.nandaiqbalh.themovielisting.wrapper.Resource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var movieRepository: MovieRepository
    private lateinit var userRepository: UserRepository


    @Before
    fun setUp() {
        viewModel = mockk()
    }


    @Test
    fun getPopularMovieListResult() {
        val expectedResult = mockk<LiveData<Resource<Popular>>>()
        every {
            viewModel.getPopularResult
        } returns expectedResult
        val actualResult = viewModel.getPopularResult.getOrAwaitValue()
        assertEquals(expectedResult, actualResult)
    }

}
package com.nandaiqbalh.themovielisting.presentation.ui.movie.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandaiqbalh.themovielisting.data.network.firebase.model.User
import com.nandaiqbalh.themovielisting.data.network.movie.model.popular.Popular
import com.nandaiqbalh.themovielisting.data.network.movie.model.toprated.TopRated
import com.nandaiqbalh.themovielisting.databinding.FragmentHomeBinding
import com.nandaiqbalh.themovielisting.presentation.ui.movie.home.adapter.PopularAdapter
import com.nandaiqbalh.themovielisting.presentation.ui.movie.home.adapter.TopRatedAdapter
import com.nandaiqbalh.themovielisting.presentation.ui.user.login.LoginFragment
import com.nandaiqbalh.themovielisting.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()
        observeData()
    }


    fun getInitialUser(user: User) {
        binding.tvWelcomeUser.text = user.username
    }

    private fun fetchData() {
        viewModel.getPopular()
        viewModel.getTopRated()
    }

    private fun setPopularRV(movie: Popular?) {
        val adapter = PopularAdapter()
        adapter.setList(movie?.results)

        adapter.itemClickListener = {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.id!!)
            findNavController().navigate(action)
        }

        binding.apply {
            rvListPopular.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvListPopular.adapter = adapter
        }

        viewModel.listStateParcel?.let { parcelable ->
            binding.rvListPopular.layoutManager?.onRestoreInstanceState(parcelable)
            viewModel.listStateParcel = null
        }
    }

    private fun setTopRatedRV(movie: TopRated?) {
        val adapter = TopRatedAdapter()
        adapter.setList(movie?.results)

        adapter.itemClickListener = {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.id!!)
            findNavController().navigate(action)
        }
        binding.apply {
            rvListTopRated.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvListTopRated.adapter = adapter
        }

        viewModel.listStateParcel?.let { parcelable ->
            binding.rvListTopRated.layoutManager?.onRestoreInstanceState(parcelable)
            viewModel.listStateParcel = null
        }
    }

    private fun observeData() {
        viewModel.getUserDetail(this@HomeFragment)

        viewModel.getPopularResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    setErrorState(false)
                }
                is Resource.Error -> {
                    setErrorState(true, it.exception.toString())
                }
                is Resource.Success -> {
                    setErrorState(false)
                    setPopularRV(it.payload)
                }
                else -> {}
            }
        }

        viewModel.getTopRatedResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    setErrorState(false)
                }
                is Resource.Error -> {
                    setErrorState(true, it.exception.toString())
                }
                is Resource.Success -> {
                    setErrorState(false)
                    setTopRatedRV(it.payload)
                }
                else -> {}
            }
        }
    }

    private fun setErrorState(isError: Boolean, message: String? = "") {
        binding.tvError.isVisible = isError
        binding.tvError.text = message
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
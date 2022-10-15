package com.nandaiqbalh.themovielisting.presentation.ui.movie.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandaiqbalh.themovielisting.data.network.model.popular.Popular
import com.nandaiqbalh.themovielisting.data.network.model.toprated.TopRated
import com.nandaiqbalh.themovielisting.databinding.FragmentHomeBinding
import com.nandaiqbalh.themovielisting.di.MovieServiceLocator
import com.nandaiqbalh.themovielisting.presentation.ui.movie.home.adapter.PopularAdapter
import com.nandaiqbalh.themovielisting.presentation.ui.movie.home.adapter.TopRatedAdapter
import com.nandaiqbalh.themovielisting.util.viewModelFactory
import com.nandaiqbalh.themovielisting.wrapper.Resource

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModelFactory {
        HomeViewModel(MovieServiceLocator.provideMovieRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()
        observeData()
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
    }

    private fun observeData() {
        viewModel.getPopularResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Error -> {}
                is Resource.Success -> {
                    setPopularRV(it.payload)
                }
                else -> {}
            }
        }

        viewModel.getTopRatedResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Error -> {}
                is Resource.Success -> {
                    setTopRatedRV(it.payload)
                }
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
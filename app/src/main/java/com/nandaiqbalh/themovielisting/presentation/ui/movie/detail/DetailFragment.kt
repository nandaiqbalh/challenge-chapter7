package com.nandaiqbalh.themovielisting.presentation.ui.movie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nandaiqbalh.themovielisting.data.network.movie.model.detail.DetailMovie
import com.nandaiqbalh.themovielisting.data.network.movie.model.detail.Genre
import com.nandaiqbalh.themovielisting.data.network.movie.model.detail.SpokenLanguage
import com.nandaiqbalh.themovielisting.databinding.FragmentDetailBinding
import com.nandaiqbalh.themovielisting.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        observeData()
    }

    private fun observeData() {
        viewModel.detailResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Empty -> {}
                is Resource.Error -> {}
                is Resource.Success -> {
                    setView(it.payload)
                }
            }
        }
    }

    private fun setView(movie: DetailMovie?) {

        movie?.let {
            binding.apply {
                Glide.with(this@DetailFragment)
                    .load(IMAGE_URL + movie.backdropPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivMovieImage)

                tvMovieTitle.text = movie.title
                tvGenre.text = setGenre(movie.genres)
                tvLanguage.text = setLanguage(movie.originalLanguage, movie.spokenLanguages)
                titleOverview.text = movie.originalTitle
                tvOverview.text = movie.overview
            }
        }
    }

    private fun setGenre(genres: List<Genre?>?): String {
        val genre = StringBuilder()
        var separator = ""
        genres?.let {
            for (i in genres) {
                genre.append(separator).append(i?.name)
                separator = ", "
            }
        }
        return genre.toString()
    }

    private fun setLanguage(oriLang: String?, spokenLang: List<SpokenLanguage?>?): String? {
        var language: String? = ""
        spokenLang?.let {
            for (i in it) {
                i?.let {
                    if (i.iso6391 == oriLang) {
                        language = i.englishName
                    }
                }
            }
        }
        return language
    }


    private fun getData() {
        viewModel.getDetail(args.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}
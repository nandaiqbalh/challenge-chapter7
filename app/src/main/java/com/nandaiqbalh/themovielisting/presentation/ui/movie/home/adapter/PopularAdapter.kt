package com.nandaiqbalh.themovielisting.presentation.ui.movie.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nandaiqbalh.themovielisting.data.network.model.popular.PopularItem
import com.nandaiqbalh.themovielisting.databinding.ItemPopularMovieBinding
import com.nandaiqbalh.themovielisting.presentation.ui.movie.detail.DetailFragment

class PopularAdapter: RecyclerView.Adapter<PopularAdapter.HomeViewHolder>() {

    var itemClickListener: ((item: PopularItem) -> Unit)? = null

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    private val diffCallback = object : DiffUtil.ItemCallback<PopularItem>() {
        override fun areItemsTheSame(oldItem: PopularItem, newItem: PopularItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PopularItem, newItem: PopularItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun setList(movie: List<PopularItem>?) {
        differ.submitList(movie)
    }

    inner class HomeViewHolder(private val binding: ItemPopularMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: PopularItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(IMAGE_URL + movie.posterPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivPosterImage)
            }

            binding.root.setOnClickListener {
                itemClickListener?.invoke(movie)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: PopularItem)
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}
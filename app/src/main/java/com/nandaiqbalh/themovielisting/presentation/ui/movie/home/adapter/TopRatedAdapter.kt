package com.nandaiqbalh.themovielisting.presentation.ui.movie.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nandaiqbalh.themovielisting.data.network.model.toprated.TopRatedItem
import com.nandaiqbalh.themovielisting.databinding.ItemPopularMovieBinding
import com.nandaiqbalh.themovielisting.databinding.ItemTopRatedMovieBinding

class TopRatedAdapter: RecyclerView.Adapter<TopRatedAdapter.HomeViewHolder>() {

    var itemClickListener: ((item: TopRatedItem) -> Unit)? = null

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    private val diffCallback = object : DiffUtil.ItemCallback<TopRatedItem>() {
        override fun areItemsTheSame(oldItem: TopRatedItem, newItem: TopRatedItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TopRatedItem, newItem: TopRatedItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun setList(movie: ArrayList<TopRatedItem>?) {
        differ.submitList(movie)
    }

    inner class HomeViewHolder(private val binding: ItemTopRatedMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: TopRatedItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(IMAGE_URL + movie.posterPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivPosterImage)

                binding.tvTitle.text = movie.title
                binding.tvLanguage.text = movie.originalLanguage

            }
            binding.root.setOnClickListener {
                itemClickListener?.invoke(movie)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemTopRatedMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: TopRatedItem)
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}
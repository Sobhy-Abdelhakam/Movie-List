package com.example.movielist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.databinding.ItemMovieBinding
import com.example.movielist.domain.model.Movie

class PopularRVAdapter(
//    private val popularList: PagingData<Movie>,
    private val itemClickListener: ItemClickListener
): PagingDataAdapter<Movie, RecyclerView.ViewHolder>(COMPARATOR) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            (holder as PopularViewHolder).bind(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return PopularViewHolder.create(parent)
    }

    class PopularViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): PopularViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(inflater, parent, false)
                return PopularViewHolder(binding)
            }
        }
        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .into(binding.itemMovieImage)
            binding.itemMovieName.text = movie.title
//            itemView.setOnClickListener {
//                itemClickListener.onItemClick(movie.id)
//            }
        }
    }
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
        }
    }
}
//class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
//    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
//    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
//}
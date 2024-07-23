package com.example.movielist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielist.databinding.ItemMovieBinding
import com.example.movielist.domain.model.Movie

class MoviesViewHolder(
    private val binding: ItemMovieBinding,
    private val itemClickListener: ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup, itemClickListener: ItemClickListener): MoviesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMovieBinding.inflate(inflater, parent, false)
            return MoviesViewHolder(binding, itemClickListener)
        }
    }

    fun bind(movie: Movie) {
        Glide.with(itemView)
            .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
            .into(binding.itemMovieImage)
        binding.itemMovieName.text = movie.title
        itemView.setOnClickListener {
            itemClickListener.onItemClick(movie.id)
        }
    }
}
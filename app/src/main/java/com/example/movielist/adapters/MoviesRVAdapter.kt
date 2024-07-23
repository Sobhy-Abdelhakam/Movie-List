package com.example.movielist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielist.databinding.ItemMovieBinding
import com.example.movielist.domain.model.Movie

class MoviesRVAdapter(
    private val itemClickListener: ItemClickListener
): PagingDataAdapter<Movie, RecyclerView.ViewHolder>(COMPARATOR) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            (holder as MoviesViewHolder).bind(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder.create(parent, itemClickListener)
    }
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
        }
    }
}
package com.example.movielist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.domain.model.Movie

class PopularRVAdapter(
    private val popularList: List<Movie>,
    private val itemClickListener: ItemClickListener
): RecyclerView.Adapter<PopularRVAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return PopularViewHolder(view)
    }
    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val movie = popularList[position]
        holder.bind(movie)
    }
    override fun getItemCount() = popularList.size

    inner class PopularViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.item_movie_image)
        private val title: TextView = itemView.findViewById(R.id.item_movie_name)
        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .into(image)
            title.text = movie.title
            itemView.setOnClickListener {
                itemClickListener.onItemClick(movie.id)
            }
        }
    }
}
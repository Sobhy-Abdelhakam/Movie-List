package com.example.movielist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.movielist.R
import com.example.movielist.databinding.ItemLoadStateBinding

class MoviesLoadStateViewHolder(
    private val binding: ItemLoadStateBinding,
    retry: () -> Unit
): RecyclerView.ViewHolder(binding.root) {
    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }
    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): MoviesLoadStateViewHolder {
            val binding = ItemLoadStateBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_load_state,
                    parent,
                    false
                )
            )
            return MoviesLoadStateViewHolder(binding, retry)
        }
    }
    fun bind(loadState: LoadState){
        if (loadState is LoadState.Error){
            binding.errorMessage.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMessage.isVisible = loadState !is LoadState.Loading
    }
}
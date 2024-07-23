package com.example.movielist.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class PopularLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<PopularLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PopularLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): PopularLoadStateViewHolder {
        return PopularLoadStateViewHolder.create(parent, retry)
    }
}
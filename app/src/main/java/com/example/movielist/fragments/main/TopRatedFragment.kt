package com.example.movielist.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movielist.R
import com.example.movielist.adapters.ItemClickListener
import com.example.movielist.adapters.MoviesLoadStateAdapter
import com.example.movielist.adapters.MoviesRVAdapter
import com.example.movielist.databinding.FragmentTopRatedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopRatedFragment : Fragment(), ItemClickListener {
    private lateinit var binding : FragmentTopRatedBinding
    private val viewModel : PopularViewModel by viewModels()
    private val adapter by lazy { MoviesRVAdapter(this) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvToprateMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvToprateMovies.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MoviesLoadStateAdapter { adapter.retry() },
            footer = MoviesLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            binding.rvToprateMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressToprateMovie.isVisible = loadState.source.refresh is LoadState.Loading
            binding.tvErrorToprateMovies.isVisible = loadState.source.refresh is LoadState.Error
//            handleError(loadState)
        }
        viewModel.topRatedMovies.observe(viewLifecycleOwner) {
            if (it.loading){
                binding.progressToprateMovie.visibility = View.VISIBLE
                binding.tvErrorToprateMovies.visibility = View.GONE
                binding.rvToprateMovies.visibility = View.GONE
            } else if (it.error.isNotBlank()){
                binding.progressToprateMovie.visibility = View.GONE
                binding.tvErrorToprateMovies.visibility = View.VISIBLE
                binding.tvErrorToprateMovies.text = it.error
                binding.rvToprateMovies.visibility = View.GONE
            } else {
                binding.progressToprateMovie.visibility = View.GONE
                binding.tvErrorToprateMovies.visibility = View.GONE
                binding.rvToprateMovies.visibility = View.VISIBLE
                lifecycleScope.launch {
                    adapter.submitData(it.topRatedMovies!!)
                }
            }
        }
    }

    override fun onItemClick(movieId: Long) {
        val bundle = Bundle().apply {
            putLong("movieId", movieId)
        }
        findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
    }
}
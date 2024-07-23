package com.example.movielist.fragments.main

import android.os.Bundle
import android.util.Log
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
import com.example.movielist.databinding.FragmentPopularMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment : Fragment(), ItemClickListener {
    private lateinit var binding : FragmentPopularMoviesBinding
    private val viewModel : PopularViewModel by viewModels()
    private val adapter by lazy { MoviesRVAdapter(this) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvPopularMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvPopularMovies.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MoviesLoadStateAdapter { adapter.retry() },
            footer = MoviesLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            binding.rvPopularMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressPopularMovie.isVisible = loadState.source.refresh is LoadState.Loading
            binding.tvErrorPopularMovies.isVisible = loadState.source.refresh is LoadState.Error
//            handleError(loadState)
        }


        viewModel.popularMovies.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieState.Loading -> {
                    binding.progressPopularMovie.visibility = View.VISIBLE
                    binding.tvErrorPopularMovies.visibility = View.GONE
                    binding.rvPopularMovies.visibility = View.GONE
                    Log.d("MovieListFragment", "Loading state")
                }
                is MovieState.Error -> {
                    binding.progressPopularMovie.visibility = View.GONE
                    binding.tvErrorPopularMovies.visibility = View.VISIBLE
                    binding.tvErrorPopularMovies.text = state.message
                    binding.rvPopularMovies.visibility = View.GONE
                    Log.d("MovieListFragment", "Error state: ${state.message}")
                }
                is MovieState.Success -> {
                    binding.progressPopularMovie.visibility = View.GONE
                    binding.tvErrorPopularMovies.visibility = View.GONE
                    binding.rvPopularMovies.visibility = View.VISIBLE
                    lifecycleScope.launch {
                        adapter.submitData(state.data)
                    }
                    Log.d("MovieListFragment", "Success state: Data displayed")
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
package com.example.movielist.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movielist.R
import com.example.movielist.adapters.ItemClickListener
import com.example.movielist.adapters.MoviesLoadStateAdapter
import com.example.movielist.adapters.MoviesRVAdapter
import com.example.movielist.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), ItemClickListener {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val adapter by lazy { MoviesRVAdapter(this) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.searchRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MoviesLoadStateAdapter { adapter.retry() },
            footer = MoviesLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            binding.searchRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.searchProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.searchNoResultTextView.isVisible = loadState.source.refresh is LoadState.Error
//            handleError(loadState)
        }
        binding.searchEditText.addTextChangedListener {text ->
            viewModel.getSearchResult(text.toString())
        }
        viewModel.searchMovies.observe(viewLifecycleOwner) {
            if (it.loading){
                binding.searchProgressBar.visibility = View.VISIBLE
                binding.searchNoResultTextView.visibility = View.GONE
                binding.searchRecyclerView.visibility = View.GONE
            } else if (it.error.isNotBlank()){
                binding.searchProgressBar.visibility = View.GONE
                binding.searchNoResultTextView.visibility = View.VISIBLE
                binding.searchNoResultTextView.text = it.error
                binding.searchRecyclerView.visibility = View.GONE
            } else {
                binding.searchProgressBar.visibility = View.GONE
                binding.searchNoResultTextView.visibility = View.GONE
                binding.searchRecyclerView.visibility = View.VISIBLE
                adapter.submitData(viewLifecycleOwner.lifecycle, it.search!!)
            }
        }
    }

    override fun onItemClick(movieId: Long) {
        val bundle = Bundle().apply {
            putLong("movieId", movieId)
        }
        findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle)
    }
}
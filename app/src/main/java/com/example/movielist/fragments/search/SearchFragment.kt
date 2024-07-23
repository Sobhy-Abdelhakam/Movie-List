package com.example.movielist.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movielist.R
import com.example.movielist.adapters.ItemClickListener
import com.example.movielist.adapters.PopularRVAdapter
import com.example.movielist.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), ItemClickListener {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchEditText.addTextChangedListener {text ->
            viewModel.getSearchResult(text.toString())
        }
        viewModel.SearchMovies.observe(viewLifecycleOwner) {
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
                binding.searchRecyclerView.adapter = PopularRVAdapter(this)
                binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
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
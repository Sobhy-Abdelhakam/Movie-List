package com.example.movielist.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movielist.R
import com.example.movielist.adapters.ItemClickListener
import com.example.movielist.adapters.PopularRVAdapter
import com.example.movielist.databinding.FragmentPopularMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : Fragment(), ItemClickListener {
    private lateinit var binding : FragmentPopularMoviesBinding
    private val viewModel : PopularViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.popularMovies.observe(viewLifecycleOwner) {
            if (it.loading){
                binding.progressPopularMovie.visibility = View.VISIBLE
                binding.tvErrorPopularMovies.visibility = View.GONE
                binding.rvPopularMovies.visibility = View.GONE
            } else if (it.error.isNotBlank()){
                binding.progressPopularMovie.visibility = View.GONE
                binding.tvErrorPopularMovies.visibility = View.VISIBLE
                binding.tvErrorPopularMovies.text = it.error
                binding.rvPopularMovies.visibility = View.GONE
            } else {
                binding.progressPopularMovie.visibility = View.GONE
                binding.tvErrorPopularMovies.visibility = View.GONE
                binding.rvPopularMovies.visibility = View.VISIBLE
                binding.rvPopularMovies.adapter = PopularRVAdapter(it.popularMovies, this)
                binding.rvPopularMovies.layoutManager = GridLayoutManager(requireContext(), 2)
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
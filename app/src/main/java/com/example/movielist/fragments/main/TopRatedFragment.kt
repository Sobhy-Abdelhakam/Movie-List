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
import com.example.movielist.databinding.FragmentTopRatedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedFragment : Fragment(), ItemClickListener {
    private lateinit var binding : FragmentTopRatedBinding
    private val viewModel : PopularViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                binding.rvToprateMovies.adapter = PopularRVAdapter(it.topRatedMovies, this)
                binding.rvToprateMovies.layoutManager = GridLayoutManager(requireContext(), 2)
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
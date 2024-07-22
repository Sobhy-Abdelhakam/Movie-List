package com.example.movielist.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.movielist.R
import com.example.movielist.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getLong("movieId")
        id?.let { viewModel.getMovie(it) }
        viewModel.movie.observe(viewLifecycleOwner) { state ->
            if (state.isLoading){
                binding.detailsProgress.visibility = View.VISIBLE
                binding.detailsError.visibility = View.GONE
                binding.detailsImage.visibility = View.GONE
                binding.detailsTitle.visibility = View.GONE
                binding.detailsDescription.visibility = View.GONE
                binding.detailsRating.visibility = View.GONE
            } else if (state.error != null){
                binding.detailsProgress.visibility = View.GONE
                binding.detailsError.visibility = View.VISIBLE
                binding.detailsError.text = state.error
                binding.detailsImage.visibility = View.GONE
                binding.detailsTitle.visibility = View.GONE
                binding.detailsDescription.visibility = View.GONE
                binding.detailsRating.visibility = View.GONE
            } else {
                binding.detailsProgress.visibility = View.GONE
                binding.detailsError.visibility = View.GONE
                binding.detailsImage.visibility = View.VISIBLE
                binding.detailsTitle.visibility = View.VISIBLE
                binding.detailsDescription.visibility = View.VISIBLE
                binding.detailsRating.visibility = View.VISIBLE
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500" + state.movie?.posterPath)
                    .into(binding.detailsImage)
                binding.detailsTitle.text = state.movie?.title
                binding.detailsRating.rating = (state.movie?.voteAverage ?: 0f) /2
                binding.detailsDescription.text = state.movie?.overview
            }
        }
    }
}
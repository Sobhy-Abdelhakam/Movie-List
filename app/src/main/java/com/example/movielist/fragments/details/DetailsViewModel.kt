package com.example.movielist.fragments.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {
    private val _movie = MutableLiveData<DetailsState>()
    val movie: LiveData<DetailsState> get() = _movie
    fun getMovie(id: Long) {
        _movie.value = DetailsState(isLoading = true)
        viewModelScope.launch {
            try {
                val movie = movieRepository.getMovieById(id)
                _movie.value = DetailsState(movie = movie)
            } catch (e: Exception) {
                _movie.value = DetailsState(error = e.localizedMessage)
            }
        }
    }
}

class DetailsState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
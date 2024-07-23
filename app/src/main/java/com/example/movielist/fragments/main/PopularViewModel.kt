package com.example.movielist.fragments.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(private val repository: MovieRepository): ViewModel() {
    private val _popularMovies = MutableLiveData<MovieState>()
    val popularMovies: LiveData<MovieState> = _popularMovies

    private val _topRatedMovies = MutableLiveData<TopRatedState>()
    val topRatedMovies: LiveData<TopRatedState> = _topRatedMovies

    init {
        getPopularMovies()
        getTopRatedMovies()
    }
    private fun getPopularMovies() {
        _popularMovies.value = MovieState.Loading
        viewModelScope.launch {
            try {
                repository.getPopularMovies()
                    .cachedIn(viewModelScope)
                    .collect{
                        _popularMovies.value = MovieState.Success(it)
                        Log.d("MovieViewModel", "Success: Data fetched")
                    }
            } catch (e: Exception) {
                _popularMovies.value = MovieState.Error(e.message ?: "Unknown error")
                Log.d("MovieViewModel", "Error: ${e.message}")
            }
        }
    }
    private fun getTopRatedMovies() {
        _topRatedMovies.value = TopRatedState(loading = true)
        viewModelScope.launch {
            try {
                val movies = repository.getTopRatedMovies()
                movies.collectLatest { result ->
                    if (result.isSuccess){
                        _topRatedMovies.value = TopRatedState(topRatedMovies = result.getOrNull()?.movies ?: emptyList())
                    } else {
                        _topRatedMovies.value = TopRatedState(error = result.exceptionOrNull()?.localizedMessage ?: "An error occurred")
                    }
                }
            } catch (e: Exception) {
                _topRatedMovies.value = TopRatedState(error = e.localizedMessage ?: "An error occurred")
            }
        }
    }
}

data class PopularState(
    val popularMovies: PagingData<Movie>? = null,
    val error: String = "",
    val loading: Boolean = false
)
sealed class MovieState {
    object Loading : MovieState()
    data class Success(val data: PagingData<Movie>) : MovieState()
    data class Error(val message: String) : MovieState()
}
data class TopRatedState(
    val topRatedMovies: List<Movie> = emptyList(),
    val error: String = "",
    val loading: Boolean = false
)
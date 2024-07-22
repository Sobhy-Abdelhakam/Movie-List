package com.example.movielist.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(private val repository: MovieRepository): ViewModel() {
    private val _popularMovies = MutableLiveData<PopularState>()
    val popularMovies: LiveData<PopularState> = _popularMovies

    private val _topRatedMovies = MutableLiveData<TopRatedState>()
    val topRatedMovies: LiveData<TopRatedState> = _topRatedMovies

    init {
        getPopularMovies()
        getTopRatedMovies()
    }
    private fun getPopularMovies() {
        _popularMovies.value = PopularState(loading = true)
        viewModelScope.launch {
            try {
                val movies = repository.getPopularMovies()
                movies.collectLatest { result ->
                    if (result.isSuccess){
                        _popularMovies.value = PopularState(popularMovies = result.getOrNull()?.movies ?: emptyList())
                    } else {
                        _popularMovies.value = PopularState(error = result.exceptionOrNull()?.localizedMessage ?: "An error occurred")
                    }
                }
            } catch (e: Exception) {
                _popularMovies.value = PopularState(error = e.localizedMessage ?: "An error occurred")
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
    val popularMovies: List<Movie> = emptyList(),
    val error: String = "",
    val loading: Boolean = false
)
data class TopRatedState(
    val topRatedMovies: List<Movie> = emptyList(),
    val error: String = "",
    val loading: Boolean = false
)
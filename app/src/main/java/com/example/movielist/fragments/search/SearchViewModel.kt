package com.example.movielist.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: MovieRepository): ViewModel() {
    private val _searchMovies = MutableLiveData<SearchStata>()
    val searchMovies: LiveData<SearchStata> = _searchMovies

    fun getSearchResult(query: String){
        _searchMovies.value = SearchStata(loading = true)
        viewModelScope.launch {
            try {
                val movies = repository.getMovieBySearch(query)
                movies.collectLatest { result ->
                        _searchMovies.value = SearchStata(search = result)
                }
            } catch (e: Exception) {
                _searchMovies.value = SearchStata(error = e.localizedMessage ?: "An error occurred")
            }
        }
    }
}

data class SearchStata(
    val search: PagingData<Movie>? = null,
    val error: String = "",
    val loading: Boolean = false
)
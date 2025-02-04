package com.example.movielist.domain.repository

import androidx.paging.PagingData
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.model.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>
    suspend fun getTopRatedMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieBySearch(query: String): Flow<PagingData<Movie>>
    suspend fun getMovieById(id: Long): Movie
}
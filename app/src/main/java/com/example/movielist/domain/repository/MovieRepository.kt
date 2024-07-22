package com.example.movielist.domain.repository

import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.model.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(): Flow<Result<MoviesResponse>>
    suspend fun getTopRatedMovies(): Flow<Result<MoviesResponse>>
    suspend fun getMovieBySearch(query: String): Flow<Result<MoviesResponse>>
    suspend fun getMovieById(id: Long): Movie
}
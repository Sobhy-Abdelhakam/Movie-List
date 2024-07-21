package com.example.movielist.domain.repository

import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.model.MoviesResponse

interface MovieRepository {
    suspend fun getPopularMovies(): MoviesResponse
    suspend fun getTopRatedMovies(): MoviesResponse
    suspend fun getMovieBySearch(query: String): MoviesResponse
    suspend fun getMovieById(id: Int): Movie
}
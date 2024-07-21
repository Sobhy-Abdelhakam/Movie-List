package com.example.movielist.data.repository

import com.example.movielist.data.remote.ApiService
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.model.MoviesResponse
import com.example.movielist.domain.repository.MovieRepository

class MovieRepositoryImpl(private val apiService: ApiService): MovieRepository {
    override suspend fun getPopularMovies(): MoviesResponse {
        return apiService.getPopularMovies(1)
    }

    override suspend fun getTopRatedMovies(): MoviesResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieBySearch(query: String): MoviesResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieById(id: Int): Movie {
        TODO("Not yet implemented")
    }
}
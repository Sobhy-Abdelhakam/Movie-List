package com.example.movielist.data.remote

import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "76eb6eb9b0298c83768d6225a3b980ec",
    ) : MoviesResponse

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "76eb6eb9b0298c83768d6225a3b980ec",
    ): MoviesResponse

    @GET("search/movie")
    fun getMoviesSearch(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "76eb6eb9b0298c83768d6225a3b980ec",
    ) : MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "76eb6eb9b0298c83768d6225a3b980ec",
    ) : Movie
}
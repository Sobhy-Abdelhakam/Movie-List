package com.example.movielist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movielist.data.remote.ApiService
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(private val apiService: ApiService): MovieRepository {
    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MoviesRemotePagingSource(apiService){
                    apiService.getPopularMovies(it)
                }
            }
        ).flow
    }

    override suspend fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MoviesRemotePagingSource(apiService){
                    apiService.getTopRatedMovies(it)
                }
            }
        ).flow
    }

    override suspend fun getMovieBySearch(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MoviesRemotePagingSource(apiService) {
                    apiService.getMoviesSearch(query, it)
                }
            }
        ).flow
    }

    override suspend fun getMovieById(id: Long): Movie {
        val response = apiService.getMovieDetails(id)
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            throw Exception(response.message())
        }
    }
}
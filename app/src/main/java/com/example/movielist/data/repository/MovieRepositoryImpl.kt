package com.example.movielist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movielist.data.remote.ApiService
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.model.MoviesResponse
import com.example.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(private val apiService: ApiService): MovieRepository {
    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PopularRemotePagingSource(apiService) }
        ).flow
    }

    override suspend fun getTopRatedMovies(): Flow<Result<MoviesResponse>> {
        return flow {
            val response = apiService.getTopRatedMovies(1)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.success(it))
                } ?: emit(Result.failure(Exception("Response body is null")))
            } else {
                emit(Result.failure(Exception(response.message())))
            }
        }
    }

    override suspend fun getMovieBySearch(query: String): Flow<Result<MoviesResponse>> {
        return flow {
            val response = apiService.getMoviesSearch(query, 1)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.success(it))
                } ?: emit(Result.failure(Exception("Response body is null")))
            } else {
                emit(Result.failure(Exception(response.message())))
            }
        }
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
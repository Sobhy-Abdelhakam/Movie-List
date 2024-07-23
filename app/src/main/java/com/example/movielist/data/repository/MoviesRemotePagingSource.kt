package com.example.movielist.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movielist.data.remote.ApiService
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.model.MoviesResponse
import kotlinx.coroutines.delay
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MoviesRemotePagingSource(
    private val apiService: ApiService,
    private val requestFunction: suspend (Int) -> Response<MoviesResponse>
): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val currentPage = params.key ?: 1
        return try {
            val response = requestFunction(currentPage)
            delay(2000)
            if (response.isSuccessful){
                val endOfPaginationReached = response.body()!!.totalPages
                val movies = response.body()?.movies ?: emptyList()
                LoadResult.Page(
                    data = movies,
                    prevKey = if (currentPage == 1) null else currentPage -1,
                    nextKey = if (currentPage == endOfPaginationReached || movies.isEmpty()) null else currentPage.plus(1)
                )
            } else {
                LoadResult.Error(Exception(response.message()))
            }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }
}
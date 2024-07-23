package com.example.movielist.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movielist.domain.entities.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<MovieEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies")
    fun deleteAllMovies()

    @Query("SELECT * FROM movies")
    fun pagingSource(): PagingSource<Int, MovieEntity>
}
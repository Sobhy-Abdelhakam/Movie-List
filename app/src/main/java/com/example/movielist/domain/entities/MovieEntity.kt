package com.example.movielist.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val overview: String,
    val voteAverage: Float,
    val releaseDate: String
)

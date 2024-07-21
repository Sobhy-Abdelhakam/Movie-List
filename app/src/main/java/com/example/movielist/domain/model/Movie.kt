package com.example.movielist.domain.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Long,
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val overview: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("release_date")
    val releaseDate: String
)

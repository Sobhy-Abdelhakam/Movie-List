package com.example.movielist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movielist.domain.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
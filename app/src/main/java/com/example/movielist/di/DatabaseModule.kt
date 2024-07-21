package com.example.movielist.di

import android.content.Context
import androidx.room.Room
import com.example.movielist.data.local.MovieDao
import com.example.movielist.data.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_database",
        ).allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideProfileDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }
}
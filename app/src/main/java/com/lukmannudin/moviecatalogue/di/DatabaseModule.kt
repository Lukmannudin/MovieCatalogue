package com.lukmannudin.moviecatalogue.di

import android.content.Context
import androidx.room.Room
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Lukmannudin on 19/05/21.
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCatalogueMovieDatabase(@ApplicationContext context: Context): MovieCatalogueDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieCatalogueDatabase::class.java,
            "movieCatalogue.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieCatalogueDatabase: MovieCatalogueDatabase): MovieDao {
        return movieCatalogueDatabase.movieDao()
    }
}
package com.lukmannudin.moviecatalogue.di

import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepository
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepositoryImpl
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemoteDataSource
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepository
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepositoryImpl
import com.lukmannudin.moviecatalogue.data.tvshowssource.remote.TvShowRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Created by Lukmannudin on 09/05/21.
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        coroutineDispatcher: CoroutineDispatcher
    ) : MovieRepository =
        MovieRepositoryImpl(movieRemoteDataSource, coroutineDispatcher)

    @Provides
    @Singleton
    fun provideTvShowsRepository(
        tvShowRemoteDataSource: TvShowRemoteDataSource,
        coroutineDispatcher: CoroutineDispatcher
    ) : TvShowRepository =
        TvShowRepositoryImpl(tvShowRemoteDataSource, coroutineDispatcher)
}


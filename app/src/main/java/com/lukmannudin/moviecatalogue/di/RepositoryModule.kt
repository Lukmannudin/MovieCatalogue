package com.lukmannudin.moviecatalogue.di

import androidx.paging.ExperimentalPagingApi
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepository
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepositoryImpl
import com.lukmannudin.moviecatalogue.data.moviessource.MoviesMediator
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieDao
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocalDataSource
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
    fun provideRemoteMovieDataSource(apiHelper: ApiHelper): MovieRemoteDataSource =
        MovieRemoteDataSource(apiHelper)

    @Provides
    @Singleton
    fun provideLocalMovieDataSource(movieDao: MovieDao): MovieLocalDataSource =
        MovieLocalDataSource(movieDao)

//    @Provides
//    @Singleton
//    fun provideMoviesRepository(
//        movieRemoteDataSource: MovieRemoteDataSource,
//        movieLocalDataSource: MovieLocalDataSource,
//        coroutineDispatcher: CoroutineDispatcher
//    ): MovieRepository =
//        MovieRepositoryImpl(movieRemoteDataSource, movieLocalDataSource, coroutineDispatcher)

    @Provides
    @Singleton
    fun provideTvShowsRepository(
        tvShowRemoteDataSource: TvShowRemoteDataSource,
        coroutineDispatcher: CoroutineDispatcher
    ): TvShowRepository =
        TvShowRepositoryImpl(tvShowRemoteDataSource, coroutineDispatcher)


    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideMovieMediator(
        apiHelper: ApiHelper,
        database: MovieCatalogueDatabase
    ): MoviesMediator =
        MoviesMediator(apiHelper, database)

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideMoviesRepository(
        database: MovieCatalogueDatabase,
        moviesMediator: MoviesMediator,
        coroutineDispatcher: CoroutineDispatcher
    ): MovieRepository =
        MovieRepositoryImpl(database, moviesMediator, coroutineDispatcher)
}


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
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowsMediator
import com.lukmannudin.moviecatalogue.data.tvshowssource.local.TvShowDao
import com.lukmannudin.moviecatalogue.data.tvshowssource.local.TvShowLocalDataSource
import com.lukmannudin.moviecatalogue.data.tvshowssource.remote.TvShowRemoteDataSource
import com.lukmannudin.moviecatalogue.utils.Constant
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
    fun provideRemoteMovieDataSource(apiHelper: ApiHelper): MovieRemoteDataSource =
        MovieRemoteDataSource(apiHelper)

    @Provides
    fun provideLocalMovieDataSource(movieDao: MovieDao): MovieLocalDataSource =
        MovieLocalDataSource(movieDao)

    @Provides
    fun provideRemoteTvShowDataSource(apiHelper: ApiHelper): TvShowRemoteDataSource =
        TvShowRemoteDataSource(apiHelper)

    @Provides
    fun provideLocalTvShowDataSource(tvShowDao: TvShowDao): TvShowLocalDataSource =
        TvShowLocalDataSource(tvShowDao)

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideTvShowsRepository(
        tvShowLocalDataSource: TvShowLocalDataSource,
        tvShowRemoteDataSource: TvShowRemoteDataSource,
        database: MovieCatalogueDatabase,
        tvShowsMediator: TvShowsMediator,
        coroutineDispatcher: CoroutineDispatcher
    ): TvShowRepository =
        TvShowRepositoryImpl(
            tvShowRemoteDataSource,
            tvShowLocalDataSource,
            database,
            tvShowsMediator,
            coroutineDispatcher
        )


    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideMovieMediator(
        apiHelper: ApiHelper,
        database: MovieCatalogueDatabase
    ): MoviesMediator =
        MoviesMediator(apiHelper, database, Constant.DEFAULT_LANGUAGE)

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideMoviesRepository(
        movieLocalDataSource: MovieLocalDataSource,
        movieRemoteDataSource: MovieRemoteDataSource,
        database: MovieCatalogueDatabase,
        moviesMediator: MoviesMediator,
        coroutineDispatcher: CoroutineDispatcher
    ): MovieRepository =
        MovieRepositoryImpl(
            movieRemoteDataSource,
            movieLocalDataSource,
            database,
            moviesMediator,
            coroutineDispatcher
        )
}


package com.lukmannudin.moviecatalogue.di

import androidx.paging.ExperimentalPagingApi
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepository
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepositoryImpl
import com.lukmannudin.moviecatalogue.data.moviessource.MoviesMediator
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocalDataSource
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemoteDataSource
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepository
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepositoryImpl
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowsMediator
import com.lukmannudin.moviecatalogue.data.tvshowssource.local.TvShowLocalDataSource
import com.lukmannudin.moviecatalogue.data.tvshowssource.remote.TvShowRemoteDataSource
import com.lukmannudin.moviecatalogue.utils.PagingCatalogueConfig
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

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideTvShowsRepository(
        tvShowLocalDataSource: TvShowLocalDataSource,
        tvShowRemoteDataSource: TvShowRemoteDataSource,
        pagingDataSource: PagingDataSource,
        ioDispatcher: CoroutineDispatcher
    ): TvShowRepository =
        TvShowRepositoryImpl(
            tvShowRemoteDataSource,
            tvShowLocalDataSource,
            pagingDataSource,
            ioDispatcher
        )


    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideMoviesRepository(
        movieLocalDataSource: MovieLocalDataSource,
        movieRemoteDataSource: MovieRemoteDataSource,
        pagingDataSource: PagingDataSource,
        coroutineDispatcher: CoroutineDispatcher
    ): MovieRepository =
        MovieRepositoryImpl(
            movieRemoteDataSource,
            movieLocalDataSource,
            pagingDataSource,
            coroutineDispatcher
        )

    @ExperimentalPagingApi
    @Provides
    fun provideMoviesMediator(
        apiHelper: ApiHelper,
        database: MovieCatalogueDatabase
    ): MoviesMediator =
        MoviesMediator(apiHelper, database, PagingCatalogueConfig.DEFAULT_LANGUAGE)

    @ExperimentalPagingApi
    @Provides
    fun provideTvShowsMediator(
        apiHelper: ApiHelper,
        database: MovieCatalogueDatabase
    ): TvShowsMediator =
        TvShowsMediator(apiHelper, database, PagingCatalogueConfig.DEFAULT_LANGUAGE)

}


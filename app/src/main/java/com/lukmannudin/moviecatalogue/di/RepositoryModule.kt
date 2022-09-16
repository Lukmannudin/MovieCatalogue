package com.lukmannudin.moviecatalogue.di

import androidx.paging.ExperimentalPagingApi
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepository
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepositoryImpl
import com.lukmannudin.moviecatalogue.data.moviessource.mediator.PopularMoviesMediator
import com.lukmannudin.moviecatalogue.data.moviessource.MoviePagingDataSource
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocalDataSource
import com.lukmannudin.moviecatalogue.data.moviessource.mediator.NowPlayingMoviesMediator
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemoteDataSource
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowPagingDataSource
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepository
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepositoryImpl
import com.lukmannudin.moviecatalogue.data.tvshowssource.mediator.TvShowsPopularMediator
import com.lukmannudin.moviecatalogue.data.tvshowssource.local.TvShowLocalDataSource
import com.lukmannudin.moviecatalogue.data.tvshowssource.mediator.TvShowsOnAirMediator
import com.lukmannudin.moviecatalogue.data.tvshowssource.remote.TvShowRemoteDataSource
import com.lukmannudin.moviecatalogue.utils.PagingCatalogueConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.*
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
        pagingDataSource: TvShowPagingDataSource,
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
        pagingDataSource: MoviePagingDataSource,
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
    fun providePopularMoviesMediator(
        apiHelper: ApiHelper,
        database: MovieCatalogueDatabase
    ): PopularMoviesMediator =
        PopularMoviesMediator(apiHelper, database, Locale.getDefault().displayLanguage)

    @ExperimentalPagingApi
    @Provides
    fun provideNowPlayingMoviesMediator(
        apiHelper: ApiHelper,
        database: MovieCatalogueDatabase
    ): NowPlayingMoviesMediator =
        NowPlayingMoviesMediator(apiHelper, database, Locale.getDefault().displayLanguage)

    @ExperimentalPagingApi
    @Provides
    fun provideTvShowsPopularMediator(
        apiHelper: ApiHelper,
        database: MovieCatalogueDatabase
    ): TvShowsPopularMediator =
        TvShowsPopularMediator(apiHelper, database, PagingCatalogueConfig.DEFAULT_LANGUAGE)

    @ExperimentalPagingApi
    @Provides
    fun provideTvShowsOnAirMediator(
        apiHelper: ApiHelper,
        database: MovieCatalogueDatabase
    ): TvShowsOnAirMediator =
        TvShowsOnAirMediator(apiHelper, database, PagingCatalogueConfig.DEFAULT_LANGUAGE)

}


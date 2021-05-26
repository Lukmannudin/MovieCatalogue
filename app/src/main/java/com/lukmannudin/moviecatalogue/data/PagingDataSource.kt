package com.lukmannudin.moviecatalogue.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.data.moviessource.MoviesMediator
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowsMediator
import com.lukmannudin.moviecatalogue.mapper.toMovieFromLocal
import com.lukmannudin.moviecatalogue.mapper.toTvShow
import com.lukmannudin.moviecatalogue.utils.PagingCatalogueConfig
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Lukmannudin on 26/05/21.
 */
@ExperimentalPagingApi
class PagingDataSource @Inject constructor(
    private val database: MovieCatalogueDatabase,
    private val moviesMediator: MoviesMediator,
    private val tvShowsMediator: TvShowsMediator
) {
    val moviesPaging = Pager(
        config = PagingConfig(PagingCatalogueConfig.DEFAULT_PAGE_SIZE),
        remoteMediator = moviesMediator
    ){
        database.movieDao().getMovies()
    }.flow.map { pagingData ->
        pagingData.map { movieLocal ->
            movieLocal.toMovieFromLocal()
        }
    }

    val tvShowsPaging = Pager(
        config = PagingConfig(PagingCatalogueConfig.DEFAULT_PAGE_SIZE),
        remoteMediator = tvShowsMediator
    ) {
        database.tvShowDao().getTvShows()
    }.flow.map { pagingData ->
        pagingData.map { tvShowLocal ->
            tvShowLocal.toTvShow()
        }
    }
}
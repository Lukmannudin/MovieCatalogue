package com.lukmannudin.moviecatalogue.data.tvshowssource

import androidx.paging.*
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.data.PagingCatalogueConfig
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.mediator.TvShowsOnAirMediator
import com.lukmannudin.moviecatalogue.data.tvshowssource.mediator.TvShowsPopularMediator
import com.lukmannudin.moviecatalogue.mapper.toTvShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
class TvShowPagingDataSource @Inject constructor(
    val database: MovieCatalogueDatabase,
    private val tvShowsPopularMediator: TvShowsPopularMediator,
    private val tvShowsOnAirMediator: TvShowsOnAirMediator
) : PagingDataSource<TvShow> {

    override fun getPopularItems(): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(PagingCatalogueConfig.DEFAULT_PAGE_SIZE),
            remoteMediator = tvShowsPopularMediator
        ) {
            val tvShows = database.tvShowDao().getPopularTvShows()
            tvShows
        }.flow.map { pagingData ->
            pagingData.map { tvShowLocal ->
                tvShowLocal.toTvShow()
            }
        }
    }

    override fun getNowPlayingItems(): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(PagingCatalogueConfig.DEFAULT_PAGE_SIZE),
            remoteMediator = tvShowsOnAirMediator
        ) {
            val tvShows = database.tvShowDao().getLatestReleases()
            tvShows
        }.flow.map { pagingData ->
            pagingData.map { tvShowLocal ->
                tvShowLocal.toTvShow()
            }
        }
    }
}
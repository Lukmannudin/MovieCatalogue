package com.lukmannudin.moviecatalogue.data.tvshowssource

import androidx.paging.*
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.data.PagingCatalogueConfig
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.mapper.toTvShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
class TvShowPagingDataSource @Inject constructor(
    val database: MovieCatalogueDatabase,
    val tvShowsMediator: TvShowsMediator
) : PagingDataSource<TvShow> {

    override fun getPopularItems(): Flow<PagingData<TvShow>> {
        return Pager(
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
}
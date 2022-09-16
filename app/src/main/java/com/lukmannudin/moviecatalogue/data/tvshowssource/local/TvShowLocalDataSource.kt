package com.lukmannudin.moviecatalogue.data.tvshowssource.local

import androidx.paging.*
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowDataSource
import com.lukmannudin.moviecatalogue.mapper.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import  com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.utils.PagingCatalogueConfig

/**
 * Created by Lukmannudin on 19/05/21.
 */

class TvShowLocalDataSource @Inject constructor(
    private val tvShowDao: TvShowDao
) : TvShowDataSource {

    override suspend fun getPopularTvShows(
        language: String,
        page: Int
    ): Flow<PagingData<TvShow>> {
        return Pager(
            PagingConfig(PagingCatalogueConfig.DEFAULT_PAGE_SIZE)
        ) {
            tvShowDao.getPopularTvShows()
        }.toTvShowsFlow()
    }


    override suspend fun getFavoriteTvShows(pageSize: Int): Flow<PagingData<TvShow>>{
        return Pager(
            PagingConfig(pageSize)
        ){
            tvShowDao.getFavoriteTvShows()
        }.toTvShowsFlow()
    }

    override suspend fun getTvShow(id: Int, language: String): Result<TvShow> {
        return try {
            val tvShow = tvShowDao.getTvShow(id)
            Result.Success(tvShow.toTvShow())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveTvShows(tvShows: List<TvShow>) {
        tvShowDao.insertTvShows(tvShows.toTvShowsLocal())
    }

    override suspend fun updateFavorite(tvShow: TvShow) {
        tvShowDao.updateFavorite(tvShow.id, tvShow.isFavorite)
    }

    override suspend fun saveTvShow(tvShow: TvShow) {
        tvShowDao.insertTvShow(tvShow.toTvShowLocal())
    }

    override suspend fun updateTvShow(tvShow: TvShow) {
        tvShowDao.updateTvShow(tvShow.toTvShowLocal())
    }

    override suspend fun getOnAirTvShows(language: String, page: Int): Flow<PagingData<TvShow>> {
        return Pager(
            PagingConfig(PagingCatalogueConfig.DEFAULT_PAGE_SIZE)
        ) {
            tvShowDao.getPopularTvShows()
        }.toTvShowsFlow()
    }

    override suspend fun getLatestTvShow(language: String): Result<TvShow> {
        return try {
            val tvShow = tvShowDao.getLatestRelease()
            tvShow?.let {
                Result.Success(tvShow.toTvShow())
            } ?: kotlin.run {
                Result.Error(NullPointerException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
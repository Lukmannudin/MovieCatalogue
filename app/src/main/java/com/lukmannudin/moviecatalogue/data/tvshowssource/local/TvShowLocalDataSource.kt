package com.lukmannudin.moviecatalogue.data.tvshowssource.local

import androidx.paging.*
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.mapper.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Lukmannudin on 19/05/21.
 */

class TvShowLocalDataSource @Inject constructor(
    private val tvShowDao: TvShowDao
) {

    fun getPopularTvShow(pageSize: Int): Flow<PagingData<TvShow>> {
        return Pager(
            PagingConfig(pageSize)
        ) {
            tvShowDao.getTvShows()
        }.flow.map { pagingData ->
            pagingData.map {  tvShow ->
                tvShow.toTvShow()
            }
        }
    }

    fun getTvShow(id: Int): Result<TvShow> {
        return try {
            val tvShow = tvShowDao.getTvShow(id)
            Result.Success(tvShow.toTvShow())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun updateTvShowFavorite(tvShow: TvShow) {
        tvShowDao.updateFavorite(tvShow.id, tvShow.isFavorite)
    }

    suspend fun saveTvShow(tvShow: TvShow){
        tvShowDao.insertTvShow(tvShow.toTvShowLocal())
    }

    suspend fun updateTvShow(tvShow: TvShow){
        tvShowDao.updateTvShow(tvShow.toTvShowLocal())
    }
}
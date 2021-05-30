package com.lukmannudin.moviecatalogue.data.tvshowssource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.remote.TvShowRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */

@ExperimentalPagingApi
class TvShowRepositoryImpl @ExperimentalPagingApi
@Inject constructor(
    private val tvShowRemoteDataSource: TvShowDataSource,
    private val tvShowLocalDataSource: TvShowDataSource,
    private val pagingDataSource: PagingDataSource<TvShow>,
    private val ioDispatcher: CoroutineDispatcher
) : TvShowRepository {

    override suspend fun getPopularTvShows(): Flow<PagingData<TvShow>> {
        return pagingDataSource.getPopularItems()
    }

    override suspend fun getFavoriteTvShows(pageSize: Int): Flow<PagingData<TvShow>> {
        return tvShowLocalDataSource.getFavoriteTvShows(pageSize)
    }

    override suspend fun getTvShow(id: Int, language: String): Flow<Result<TvShow>> = flow {

        when (val responseRemote = tvShowRemoteDataSource.getTvShow(id, language)) {
            is Result.Success -> {
                val responseLocal = tvShowLocalDataSource.getTvShow(id, language)
                val remoteTvShow = responseRemote.data

                if (responseLocal is Result.Success) {
                    remoteTvShow.isFavorite = responseLocal.data.isFavorite
                    tvShowLocalDataSource.updateTvShow(remoteTvShow)
                } else {
                    tvShowLocalDataSource.saveTvShow(remoteTvShow)
                }
            }
            is Result.Error -> {
                emit(tvShowLocalDataSource.getTvShow(id, language))
            }
        }

        when (val responseLocal = tvShowLocalDataSource.getTvShow(id, language)) {
            is Result.Success -> {
                emit(Result.Success(responseLocal.data))
            }
            is Result.Error -> {
                emit(Result.Error(responseLocal.exception))
            }
        }
    }

    override suspend fun updateFavorite(tvShow: TvShow) {
        withContext(ioDispatcher) {
            tvShowLocalDataSource.updateFavorite(tvShow)
        }
    }

}
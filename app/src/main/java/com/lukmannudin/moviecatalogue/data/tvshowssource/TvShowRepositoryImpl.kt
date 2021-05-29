package com.lukmannudin.moviecatalogue.data.tvshowssource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.local.TvShowLocalDataSource
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
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val pagingDataSource: PagingDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : TvShowRepository {

    override suspend fun getPopularTvShows(): Flow<PagingData<TvShow>> {
        return pagingDataSource.tvShowsPaging
    }

    override suspend fun getTvShow(id: Int, language: String): Flow<Result<TvShow>> = flow {

        when (val responseRemote = tvShowRemoteDataSource.getTvShow(id, language)) {
            is Result.Success -> {
                val responseLocal = tvShowLocalDataSource.getTvShow(id)
                val remoteTvShow = responseRemote.data

                if (responseLocal is Result.Success){
                    remoteTvShow.isFavorite = responseLocal.data.isFavorite
                    tvShowLocalDataSource.updateTvShow(remoteTvShow)
                } else {
                    tvShowLocalDataSource.saveTvShow(remoteTvShow)
                }
            }
            is Result.Error -> {
                emit(tvShowLocalDataSource.getTvShow(id))
            }
        }

        when (val responseLocal = tvShowLocalDataSource.getTvShow(id)) {
            is Result.Success -> {
                emit(Result.Success(responseLocal.data))
            }
            is Result.Error -> {
                emit(Result.Error(responseLocal.exception))
            }
        }
    }

    override suspend fun updateFavorite(tvShow: TvShow) {
        withContext(ioDispatcher){
            tvShowLocalDataSource.updateTvShowFavorite(tvShow)
        }
    }

}
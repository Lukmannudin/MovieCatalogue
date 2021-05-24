package com.lukmannudin.moviecatalogue.data.tvshowssource

import androidx.paging.*
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.local.TvShowLocalDataSource
import com.lukmannudin.moviecatalogue.data.tvshowssource.remote.TvShowRemoteDataSource
import com.lukmannudin.moviecatalogue.mapper.toTvShow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */

@ExperimentalPagingApi
class TvShowRepositoryImpl @ExperimentalPagingApi
@Inject constructor(
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val database: MovieCatalogueDatabase,
    private val tvShowsMediator: TvShowsMediator,
    private val ioDispatcher: CoroutineDispatcher
) : TvShowRepository {

    override suspend fun getPopularTvShows(
        language: String,
        pageSize: Int
    ): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig(pageSize),
            remoteMediator = tvShowsMediator
        ) {
            database.tvShowDao().getTvShows()
        }.flow.map { pagingData ->
            pagingData.map {
                it.toTvShow()
            }
        }
    }

    override suspend fun getTvShow(id: Int, language: String): Flow<Result<TvShow>> = flow {

        when (val responseRemote = tvShowRemoteDataSource.getTvShow(id, language)) {
            is Result.Success -> {
                tvShowLocalDataSource.saveTvShow(responseRemote.data)
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

}
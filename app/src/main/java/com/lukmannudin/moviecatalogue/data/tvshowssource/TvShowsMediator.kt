package com.lukmannudin.moviecatalogue.data.tvshowssource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.tvshowssource.local.TvShowLocal
import com.lukmannudin.moviecatalogue.data.tvshowssource.local.TvShowRemoteKey
import com.lukmannudin.moviecatalogue.mapper.toTvShows
import com.lukmannudin.moviecatalogue.mapper.toTvShowsLocal
import kotlinx.coroutines.DelicateCoroutinesApi
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Created by Lukmannudin on 20/05/21.
 */
@ExperimentalPagingApi
class TvShowsMediator @Inject constructor(
    private val apiHelper: ApiHelper,
    private val database: MovieCatalogueDatabase,
    private val language: String
) : RemoteMediator<Int, TvShowLocal>() {
    private val tvShowDao = database.tvShowDao()
    private val remoteKeyDao = database.tvShowRemoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    @DelicateCoroutinesApi
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TvShowLocal>
    ): MediatorResult {
        return try {
            val lastItem = state.lastItemOrNull()

            val remoteKey = database.withTransaction {
                lastItem?.let { lastItem -> remoteKeyDao.remoteKeyById(lastItem.page) }
            }

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    remoteKey?.nextPage ?: 1
                }
            }

            val response = loadKey?.let { key ->
                apiHelper.getPopularTvShows(
                    language,
                    key
                )
            }

            database.withTransaction {
                val results = response?.body()?.results

                results?.map {
                    it.page = response.body()?.page
                }

                results.toTvShows().toTvShowsLocal().let {
                    tvShowDao.insertTvShow(
                        it
                    )

                    if (lastItem != null) {
                        remoteKeyDao.insert(
                            TvShowRemoteKey(
                                lastItem.page, lastItem.page.plus(1)
                            )
                        )
                    }
                }
            }

            var isLastPage: Boolean?

            response?.body().let { baseResponse ->
                val currentPage = baseResponse?.page
                val lastPage = baseResponse?.totalPages

                isLastPage = currentPage == lastPage
            }

            MediatorResult.Success(endOfPaginationReached = false)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
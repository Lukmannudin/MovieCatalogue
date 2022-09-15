package com.lukmannudin.moviecatalogue.data.tvshowssource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.entity.BaseResponse
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieRemoteKey
import com.lukmannudin.moviecatalogue.data.tvshowssource.local.TvShowLocal
import com.lukmannudin.moviecatalogue.data.tvshowssource.remote.TvShowRemote
import com.lukmannudin.moviecatalogue.mapper.toTvShows
import com.lukmannudin.moviecatalogue.mapper.toTvShowsLocal
import kotlinx.coroutines.DelicateCoroutinesApi
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
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
    private val remoteKeyDao = database.movieRemoteKeyDao()
    private val prependType = -1

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TvShowLocal>
    ): MediatorResult {

        return try {
            val remoteKey = initializeRemoteKey()

            val loadKey = getLoadKey(remoteKey, loadType)
            if (loadKey == prependType) return MediatorResult.Success(endOfPaginationReached = true)

            val response = remoteResponse(loadKey)

            val results = response?.body()?.results ?: return MediatorResult.Error(
                Exception("something wrong with server"))

            if (isLastPage(remoteKey, response.body()!!.totalPages)) return MediatorResult.Success(
                endOfPaginationReached = true
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    tvShowDao.clearCache()
                    remoteKeyDao.clearCache()
                }

                insertTvShowsToDB(results)

                val newNextPage = remoteKey.movieNextPage?.plus(1)
                remoteKeyDao.updateCurrentMovieNextPage(newNextPage ?: 1)
            }

            MediatorResult.Success(endOfPaginationReached = false)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private fun getLoadKey(remoteKey: MovieRemoteKey, loadType: LoadType): Int?{
        return when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> prependType
            LoadType.APPEND -> {
                remoteKey.movieNextPage
            }
        }
    }

    @Suppress("SENSELESS_COMPARISON")
    private suspend fun initializeRemoteKey(): MovieRemoteKey {
        return database.withTransaction {
            val remoteKey = remoteKeyDao.remote_key()
            if (remoteKey == null) {
                remoteKeyDao.insert(MovieRemoteKey(1, 1))
                remoteKeyDao.remote_key()
            } else {
                remoteKey
            }
        }
    }

    private fun isLastPage(remoteKey: MovieRemoteKey, lastPage:Int?): Boolean{
        if (remoteKey.movieNextPage == null || lastPage == null) return true
        return remoteKey.movieNextPage == lastPage
    }

    private suspend fun remoteResponse(loadKey: Int?): Response<BaseResponse<List<TvShowRemote>>>?{
        return loadKey?.let { key ->
            apiHelper.getPopularTvShows(
                language,
                key
            )
        }
    }

    private suspend fun insertTvShowsToDB(tvShows: List<TvShowRemote>){
        tvShows.toTvShows().toTvShowsLocal().let { tvShowsLocal ->
            tvShowDao.insertTvShows(
                tvShowsLocal
            )
        }
    }
}
package com.lukmannudin.moviecatalogue.data.moviessource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocal
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieRemoteKey
import com.lukmannudin.moviecatalogue.mapper.toMoviesFromRemote
import com.lukmannudin.moviecatalogue.mapper.toMoviesLocal
import com.lukmannudin.moviecatalogue.utils.Constant
import kotlinx.coroutines.DelicateCoroutinesApi
import okio.IOException
import retrofit2.HttpException

/**
 * Created by Lukmannudin on 20/05/21.
 */
@ExperimentalPagingApi
class MoviesMediator(
    private val apiHelper: ApiHelper,
    private val database: MovieCatalogueDatabase
) : RemoteMediator<Int, MovieLocal>() {
    val movieDao = database.movieDao()
    val remoteKeyDao = database.movieRemoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    @DelicateCoroutinesApi
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieLocal>
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
                    remoteKey?.nextPage
                }
            }

            val response = when {
                loadKey != null -> {
                    apiHelper.getPopularMovies(Constant.DEFAULT_LANGUAGE, loadKey)
                }
                remoteKey?.nextPage == null -> {
                    apiHelper.getPopularMovies(Constant.DEFAULT_LANGUAGE, 1)
                }
                else -> {
                    null
                }
            }

            if (response != null) {
                database.withTransaction {
                    val results = response.body()?.results

                    results?.map {
                        it.page = response.body()?.page
                    }

                    results.toMoviesFromRemote().toMoviesLocal().let {
                        movieDao.insertMovies(
                            it
                        )

                        if (lastItem != null){
                            remoteKeyDao.insert(
                                MovieRemoteKey(
                                    lastItem.page, lastItem.page.plus(1)
                                )
                            )
                        }
                    }
                }
            }


            MediatorResult.Success(endOfPaginationReached = response?.body()?.page == 7)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
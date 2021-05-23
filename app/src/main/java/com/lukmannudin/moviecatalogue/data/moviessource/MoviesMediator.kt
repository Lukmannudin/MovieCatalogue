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

//    override suspend fun initialize(): InitializeAction {
//        return InitializeAction.LAUNCH_INITIAL_REFRESH
//    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieLocal>
    ): MediatorResult {
        return try {
            // The network load method takes an optional `after=<user.id>` parameter. For every
            // page after the first, we pass the last user ID to let it continue from where it
            // left off. For REFRESH, pass `null` to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, we never need to prepend, since REFRESH will always load the
                // first page in the list. Immediately return, reporting end of pagination.
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {

                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    val remoteKey = database.withTransaction {
                        return@withTransaction remoteKeyDao.remoteKeyById(lastItem.id)
                    }

                    Log.d("cekcekcek remoteKey", remoteKey.toString())
                    if (remoteKey.nextPage == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.nextPage
                    // We must explicitly check if the last item is `null` when appending,
                    // since passing `null` to networkService is only valid for initial load.
                    // If lastItem is `null` it means no items were loaded after the initial
                    // REFRESH and there are no more items to load.

                }
            }

            Log.d("cekcekcekcek loadkey", loadType.toString())
            Log.d("cekcekcekcek loadkey", loadKey.toString())
            // Suspending network load via Retrofit. This doesn't need to be wrapped in a
            // withContext(Dispatcher.IO) { ... } block since Retrofit's Coroutine CallAdapter
            // dispatches on a worker thread.
            val response = apiHelper.getPopularMovies(Constant.DEFAULT_LANGUAGE, loadKey ?: 1)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.clearCache()
                }

                // Insert new users into database, which invalidates the current
                // PagingData, allowing Paging to present the updates in the DB.
                val results = response.body()?.results
                results?.map {
                    it.page = response.body()?.page
                }

                results.toMoviesFromRemote().toMoviesLocal().let {
                    movieDao.insertMovies(
                        it
                    )
                    val currentPage = response.body()?.page
                    if (currentPage != null) {
                        remoteKeyDao.insert(
                            MovieRemoteKey(
                                currentPage,
                                currentPage + 1
                            )
                        )
                    }
                }
            }

            MediatorResult.Success(endOfPaginationReached = false)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
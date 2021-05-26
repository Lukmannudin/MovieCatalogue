package com.lukmannudin.moviecatalogue.data.moviessource

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
import kotlinx.coroutines.DelicateCoroutinesApi
import okio.IOException
import retrofit2.HttpException

/**
 * Created by Lukmannudin on 20/05/21.
 */
@ExperimentalPagingApi
class MoviesMediator(
    private val apiHelper: ApiHelper,
    private val database: MovieCatalogueDatabase,
    private val language: String
) : RemoteMediator<Int, MovieLocal>() {
    private val movieDao = database.movieDao()
    private val remoteKeyDao = database.movieRemoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    @DelicateCoroutinesApi
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieLocal>
    ): MediatorResult {
        return try {


            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val nextPage = database.withTransaction {
                        val remoteKey = remoteKeyDao.remote_key()
                        remoteKey.movieNextPage
                    }
                    nextPage ?: 1
                }
            }

            val response = loadKey?.let { key ->
                apiHelper.getPopularMovies(
                    language,
                    key
                )
            }

            var isLastPage: Boolean?
            var nextPage: Int? = 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.clearCache()
                }
                val results = response?.body()?.results

                results?.map {
                    it.page = response.body()?.page
                }

                results.toMoviesFromRemote().toMoviesLocal().let {
                    movieDao.insertMovies(
                        it
                    )
                }

                val remoteKey = remoteKeyDao.remote_key()

                @Suppress("SENSELESS_COMPARISON")
                if (remoteKey != null) {
                    remoteKey.movieNextPage?.plus(1).let { page ->
                        remoteKeyDao.updateCurrentMovieNextPage(
                                page!!)
                        nextPage = page
                    }
                } else {
                    remoteKeyDao.insert(MovieRemoteKey(1, 1))
                }
            }

            response?.body().let { baseResponse ->
                val lastPage = baseResponse?.totalPages
                isLastPage = nextPage == lastPage
            }

            MediatorResult.Success(endOfPaginationReached = isLastPage ?: true)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
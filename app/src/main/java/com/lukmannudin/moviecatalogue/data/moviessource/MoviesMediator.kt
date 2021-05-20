package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieDao
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieRemoteKey
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieRemoteKeyDao
import com.lukmannudin.moviecatalogue.mapper.toMovieFromRemote
import com.lukmannudin.moviecatalogue.mapper.toMoviesFromRemote
import com.lukmannudin.moviecatalogue.mapper.toMoviesLocal
import com.lukmannudin.moviecatalogue.utils.Constant
import okio.IOException
import retrofit2.HttpException
import java.io.InvalidObjectException

/**
 * Created by Lukmannudin on 20/05/21.
 */
@ExperimentalPagingApi
class MoviesMediator(
    private val apiHelper: ApiHelper,
    private val db: MovieCatalogueDatabase
) : RemoteMediator<Int, Movie>() {

    private val remoteKeyDao: MovieRemoteKeyDao = db.movieRemoteKeyDao()
    private val movieDao: MovieDao = db.movieDao()
//    override suspend fun initialize(): InitializeAction {
//        return InitializeAction.LAUNCH_INITIAL_REFRESH
//    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        val page = when (val pagedKeyData = getPagedKeyData(loadType, state)) {
            is MediatorResult.Success -> {
                return pagedKeyData
            }
            else -> {
                pagedKeyData as Int
            }
        }

        try {
            val response =
                apiHelper.getPopularMovies(Constant.DEFAULT_LANGUAGE, Constant.DEFAULT_PAGE_INDEX)
            val isEndOfList = response.body()?.results?.isNullOrEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearCache()
                    movieDao.clearCache()
                }
            }

            val prevKey = if (page == Constant.DEFAULT_PAGE_INDEX) null else page -1
            val nextKey = if (isEndOfList!!) null else page + 1
            val keys = response.body()?.results?.map { movieRemote ->
                MovieRemoteKey(movieRemote.toMovieFromRemote().id, nextKey, prevKey)
            }
            keys?.let { remoteKeyDao.insertRemoteKeys(it) }
            movieDao.insertMovies(response.body()?.results.toMoviesFromRemote().toMoviesLocal())
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }

    suspend fun getPagedKeyData(loadType: LoadType, state: PagingState<Int, Movie>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getClosestRemoteKey(state)
                remoteKey?.nextPage?.minus(1) ?: Constant.DEFAULT_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKey = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                //end of list condition reached
                remoteKeys.nextPage
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.nextPage
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Movie>): MovieRemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movie ->
                remoteKeyDao.remoteKeyById(movie.id)
            }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Movie>): MovieRemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movie ->
                remoteKeyDao.remoteKeyById(movie.id)
            }
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, Movie>): MovieRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                remoteKeyDao.remoteKeyById(movieId)
            }
        }
    }

}
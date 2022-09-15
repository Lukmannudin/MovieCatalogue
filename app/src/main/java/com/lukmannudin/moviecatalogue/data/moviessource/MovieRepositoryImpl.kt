package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


@ExperimentalPagingApi
class MovieRepositoryImpl @ExperimentalPagingApi
@Inject constructor(
    private val movieRemoteDataSource: MovieDataSource,
    private val movieLocalDataSource: MovieDataSource,
    private val pagingDataSource: PagingDataSource<Movie>,
    private val ioDispatcher: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getPopularMovies(): Flow<PagingData<Movie>> {
        return pagingDataSource.getPopularItems()
    }

    override suspend fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return pagingDataSource.getNowPlayingItems()
    }

    override suspend fun getFavoriteMovies(pageSize: Int): Flow<PagingData<Movie>> {
        return movieLocalDataSource.getFavoriteMovies(pageSize)
    }

    override suspend fun getMovie(id: Int, language: String): Flow<Result<Movie>> = flow {
        when (val responseRemote = movieRemoteDataSource.getMovie(id, language)) {
            is Result.Success -> {
                val movie = responseRemote.data
                emit(Result.Success(movie))
                movieLocalDataSource.saveMovie(movie)
            }
            is Result.Error -> {
                emit(movieLocalDataSource.getMovie(id, language))
            }
            else -> {}
        }
    }

    override suspend fun getLatestMovie(language: String): Flow<Result<Movie>> {
        return flow {
            when (val responseRemote = movieRemoteDataSource.getLatestMovie(language)) {
                is Result.Success -> {
                    val movie = responseRemote.data
                    emit(Result.Success(movie))
                    movieLocalDataSource.saveMovie(movie)
                }
                is Result.Error -> {
                    emit(movieLocalDataSource.getLatestMovie(language))
                }
                else -> {}
            }
        }
    }

    override suspend fun updateFavorite(movie: Movie) {
        withContext(ioDispatcher) {
            movieLocalDataSource.updateFavorite(movie)
        }
    }
}
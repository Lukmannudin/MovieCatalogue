package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocalDataSource
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemoteDataSource
import com.lukmannudin.moviecatalogue.utils.EspressoIdlingResource
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

    override suspend fun getFavoriteMovies(pageSize: Int): Flow<PagingData<Movie>> {
        return movieLocalDataSource.getFavoriteMovies(pageSize)
    }

    override suspend fun getMovie(id: Int, language: String): Flow<Result<Movie>> = flow {
        when (val responseRemote = movieRemoteDataSource.getMovie(id, language)) {
            is Result.Success -> {
                val responseLocal = movieLocalDataSource.getMovie(id, language)
                val remoteMovie = responseRemote.data

                if (responseLocal is Result.Success) {
                    remoteMovie.isFavorite = responseLocal.data.isFavorite
                    movieLocalDataSource.updateMovie(remoteMovie)
                } else {
                    movieLocalDataSource.saveMovie(remoteMovie)
                }
            }
            is Result.Error -> {
                emit(movieLocalDataSource.getMovie(id, language))
            }
        }

        when (val responseLocal = movieLocalDataSource.getMovie(id, language)) {
            is Result.Success -> {
                emit(Result.Success(responseLocal.data))
            }
            is Result.Error -> {
                emit(Result.Error(responseLocal.exception))
            }
        }
    }

    override suspend fun updateFavorite(movie: Movie) {
        withContext(ioDispatcher) {
            movieLocalDataSource.updateFavorite(movie)
        }
    }
}
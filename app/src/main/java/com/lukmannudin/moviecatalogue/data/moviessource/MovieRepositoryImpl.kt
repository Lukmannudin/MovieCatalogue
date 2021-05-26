package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.*
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocalDataSource
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemoteDataSource
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
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val pagingDataSource: PagingDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getPopularMovies(
        language: String,
        pageSize: Int
    ): Flow<PagingData<Movie>> {
        return pagingDataSource.moviesPaging
    }

    override suspend fun getMovie(id: Int, language: String): Flow<Result<Movie>> = flow {
        when (val responseRemote = movieRemoteDataSource.getMovie(id, language)) {
            is Result.Success -> {
                movieLocalDataSource.saveMovie(responseRemote.data)
            }
            is Result.Error -> {
                emit(movieLocalDataSource.getMovie(id))
            }
        }

        when (val responseLocal = movieLocalDataSource.getMovie(id)) {
            is Result.Success -> {
                emit(Result.Success(responseLocal.data))
            }
            is Result.Error -> {
                emit(Result.Error(responseLocal.exception))
            }
        }
    }

    override suspend fun updateFavorite(movie: Movie) {
        withContext(ioDispatcher){
            movieLocalDataSource.updateMovie(movie)
        }
    }
}
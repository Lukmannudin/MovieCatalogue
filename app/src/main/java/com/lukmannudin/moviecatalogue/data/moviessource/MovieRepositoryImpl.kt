package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocalDataSource
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemoteDataSource
import com.lukmannudin.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val ioDispather: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getPopularMovies(
        language: String,
        pageSize: Int
    ): Flow<PagingData<Movie>> {
        movieRemoteDataSource.getPopularMovies(
            movieLocalDataSource, language, pageSize
        )

        return movieLocalDataSource.getPopularMovies(pageSize)
    }

    override suspend fun getMovie(id: Int, language: String): Result<Movie> {
        EspressoIdlingResource.increment()
        return withContext(ioDispather) {
            EspressoIdlingResource.decrement()
            when (val movieLocal = movieLocalDataSource.getMovie(id)) {
                is Result.Success -> {
                    return@withContext movieLocal
                }
                is Result.Error -> {
                    return@withContext movieRemoteDataSource.getMovie(id, language)
                }
                else -> {
                    return@withContext Result.Error(IllegalArgumentException())
                }
            }
        }
    }

}
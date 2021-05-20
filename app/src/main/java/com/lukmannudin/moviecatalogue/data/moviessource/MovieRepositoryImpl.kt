package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.DataSource
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocalDataSource
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemoteDataSource
import com.lukmannudin.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
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
    ): Result<DataSource.Factory<Int, Movie>> {
        EspressoIdlingResource.increment()

        return withContext(ioDispather) {
            when (val moviesRemote = movieRemoteDataSource.getPopularMovies(language, pageSize)) {
                is Result.Success -> {
                    movieLocalDataSource.saveMovies(moviesRemote.data)
                    return@withContext movieLocalDataSource.getPopularMovies()
                }
                is Result.Error -> {
                    return@withContext movieLocalDataSource.getPopularMovies()
                }
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
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
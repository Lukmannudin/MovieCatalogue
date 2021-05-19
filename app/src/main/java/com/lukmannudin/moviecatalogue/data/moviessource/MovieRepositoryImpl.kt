package com.lukmannudin.moviecatalogue.data.moviessource

import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieDataSource,
    private val movieLocalDataSource: MovieDataSource,
    private val ioDispather: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getPopularMovies(language: String, page: Int): Result<List<Movie>> {
        EspressoIdlingResource.increment()
        return withContext(ioDispather) {
            EspressoIdlingResource.decrement()
            when (val moviesRemote = movieRemoteDataSource.getPopularMovies(language, page)) {
                is Result.Success -> {
                    movieLocalDataSource.saveMovies(moviesRemote.data)
                    return@withContext movieLocalDataSource.getPopularMovies(language, page)
                }
                is Result.Error -> {
                    return@withContext movieLocalDataSource.getPopularMovies(language, page)
                }
                else -> {
                    return@withContext Result.Error(IllegalArgumentException())
                }
            }
        }
    }

    override suspend fun getMovie(id: Int, language: String): Result<Movie> {
        EspressoIdlingResource.increment()
        return withContext(ioDispather) {
            EspressoIdlingResource.decrement()
            when (val movieLocal = movieLocalDataSource.getMovie(id, language)) {
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
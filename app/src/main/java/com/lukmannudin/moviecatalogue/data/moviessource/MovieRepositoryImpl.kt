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
    private val ioDispather: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getPopularMovies(language: String, page:Int): Result<List<Movie>> {
        EspressoIdlingResource.increment()
        return withContext(ioDispather){
            EspressoIdlingResource.decrement()
            movieRemoteDataSource.getPopularMovies(language, page)
        }
    }

    override suspend fun getMovie(id: Int, language: String): Result<Movie> {
        EspressoIdlingResource.increment()
        return withContext(ioDispather){
            EspressoIdlingResource.decrement()
            movieRemoteDataSource.getMovie(id, language)
        }
    }
}
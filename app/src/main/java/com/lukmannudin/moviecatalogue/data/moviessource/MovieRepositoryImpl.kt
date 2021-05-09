package com.lukmannudin.moviecatalogue.data.moviessource

import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(language: String, page:Int): Result<List<Movie>> {
        return movieRemoteDataSource.getPopularMovies(language, page)
    }

    override suspend fun getMovie(id: Int, language: String): Result<Movie> {
        return movieRemoteDataSource.getMovie(id, language)
    }
}
package com.lukmannudin.moviecatalogue.data.moviessource

import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemoteDataSource
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(language: String, page:Int): Result<List<Movie>> {
        return movieRemoteDataSource.getPopularMovies(language, page)
    }
}
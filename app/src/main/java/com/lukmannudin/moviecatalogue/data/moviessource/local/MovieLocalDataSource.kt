package com.lukmannudin.moviecatalogue.data.moviessource.local

import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.moviessource.MovieDataSource
import com.lukmannudin.moviecatalogue.mapper.toMovieFromLocal
import com.lukmannudin.moviecatalogue.mapper.toMoviesFromLocal
import com.lukmannudin.moviecatalogue.mapper.toMoviesLocal
import javax.inject.Inject

/**
 * Created by Lukmannudin on 19/05/21.
 */

class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) : MovieDataSource {

    override suspend fun getPopularMovies(language: String, page: Int): Result<List<Movie>> {
        return try {
            val movies = movieDao.getMovies()
            Result.Success(movies.toMoviesFromLocal())
        } catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun getMovie(id: Int, language: String): Result<Movie> {
        return try {
            val movies = movieDao.getMovie(id)
            Result.Success(movies.toMovieFromLocal())
        } catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun saveMovies(movies: List<Movie>){
        movieDao.insertMovies(movies.toMoviesLocal())
    }
}
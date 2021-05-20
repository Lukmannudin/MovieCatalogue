package com.lukmannudin.moviecatalogue.data.moviessource.local

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.mapper.toMovieFromLocal
import com.lukmannudin.moviecatalogue.mapper.toMoviesLocal
import javax.inject.Inject

/**
 * Created by Lukmannudin on 19/05/21.
 */

class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {

    fun getPopularMovies(): Result<DataSource.Factory<Int, Movie>> {
        return try {
            val moviesDataSource = movieDao.getMovies().map {
                it.toMovieFromLocal()
            }
            Result.Success(moviesDataSource)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun getMovie(id: Int): Result<Movie> {
        return try {
            val movies = movieDao.getMovie(id)
            Result.Success(movies.toMovieFromLocal())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun saveMovies(movies: List<Movie>) {
        movieDao.insertMovies(movies.toMoviesLocal())
    }
}
package com.lukmannudin.moviecatalogue.data.moviessource.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.mapper.toMovieFromLocal
import com.lukmannudin.moviecatalogue.mapper.toMovieLocal
import com.lukmannudin.moviecatalogue.mapper.toMoviesFlow
import com.lukmannudin.moviecatalogue.mapper.toMoviesLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Lukmannudin on 19/05/21.
 */

class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {

    fun getFavoriteMovies(pageSize: Int): Flow<PagingData<Movie>>{
        return Pager(
            PagingConfig(pageSize)
        ){
            movieDao.getFavoriteMovies()
        }.toMoviesFlow()
    }

    fun getMovie(id: Int): Result<Movie> {
        return try {
            val movies = movieDao.getMovie(id)
            Result.Success(movies.toMovieFromLocal())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun updateFavorite(movie: Movie) {
        movieDao.updateFavorite(movie.id, movie.isFavorite)
    }

    suspend fun saveMovie(movie: Movie) {
        movieDao.insertMovie(movie.toMovieLocal())
    }

    suspend fun updateMovie(movie: Movie){
        movieDao.updateMovie(movie.toMovieLocal())
    }
}
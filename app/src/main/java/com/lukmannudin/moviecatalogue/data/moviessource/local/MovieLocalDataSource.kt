package com.lukmannudin.moviecatalogue.data.moviessource.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.moviessource.MovieDataSource
import com.lukmannudin.moviecatalogue.mapper.toMovieFromLocal
import com.lukmannudin.moviecatalogue.mapper.toMovieLocal
import com.lukmannudin.moviecatalogue.mapper.toMoviesFlow
import com.lukmannudin.moviecatalogue.mapper.toMoviesLocal
import com.lukmannudin.moviecatalogue.utils.PagingCatalogueConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Lukmannudin on 19/05/21.
 */

class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) : MovieDataSource {

    override suspend fun getPopularMovies(language: String, page: Int): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(PagingCatalogueConfig.DEFAULT_PAGE_SIZE)
        ) {
            movieDao.getMovies()
        }.toMoviesFlow()
    }

    override suspend fun getFavoriteMovies(pageSize: Int): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(pageSize)
        ) {
            movieDao.getFavoriteMovies()
        }.toMoviesFlow()
    }

    override suspend fun getMovie(id: Int, language: String): Result<Movie> {
        return try {
            val movies = movieDao.getMovie(id)
            Result.Success(movies.toMovieFromLocal())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        movieDao.insertMovies(movies.toMoviesLocal())
    }

    override suspend fun saveMovie(movie: Movie) {
        movieDao.insertMovie(movie.toMovieLocal())
    }

    override suspend fun updateFavorite(movie: Movie) {
        movieDao.updateFavorite(movie.id, movie.isFavorite)
    }

    override suspend fun updateMovie(movie: Movie) {
        movieDao.updateMovie(movie.toMovieLocal())
    }
}
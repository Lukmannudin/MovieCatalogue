package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import kotlinx.coroutines.flow.Flow

/**
 * Created by Lukmannudin on 09/05/21.
 */


interface MovieDataSource {

    suspend fun getPopularMovies(language: String, page: Int): Flow<PagingData<Movie>>

    suspend fun getFavoriteMovies(pageSize: Int): Flow<PagingData<Movie>>

    suspend fun getMovie(id: Int, language: String): Result<Movie>

    suspend fun saveMovies(movies: List<Movie>)

    suspend fun saveMovie(movie: Movie)

    suspend fun updateFavorite(movie: Movie)

    suspend fun updateMovie(movie: Movie)

}
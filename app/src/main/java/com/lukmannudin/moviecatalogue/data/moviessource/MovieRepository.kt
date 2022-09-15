package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import kotlinx.coroutines.flow.Flow

/**
 * Created by Lukmannudin on 09/05/21.
 */


interface MovieRepository {

    suspend fun getPopularMovies(): Flow<PagingData<Movie>>

    suspend fun getNowPlayingMovies(): Flow<PagingData<Movie>>

    suspend fun getMovie(id: Int, language: String): Flow<Result<Movie>>

    suspend fun getLatestMovie(language: String): Flow<Result<Movie>>

    suspend fun updateFavorite(movie: Movie)

    suspend fun getFavoriteMovies(pageSize: Int): Flow<PagingData<Movie>>

}
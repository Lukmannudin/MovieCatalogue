package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import kotlinx.coroutines.flow.Flow

/**
 * Created by Lukmannudin on 09/05/21.
 */


interface MovieRepository {

    suspend fun getPopularMovies(language: String, pageSize: Int): Flow<PagingData<Movie>>

    suspend fun getMovie(id: Int, language: String): Flow<Result<Movie>>

    suspend fun updateFavorite(movie: Movie)

}
package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result

/**
 * Created by Lukmannudin on 09/05/21.
 */


interface MovieRepository {

    suspend fun getPopularMovies(language: String, pageSize: Int): Result<DataSource.Factory<Int, Movie>>

    suspend fun getMovie(id: Int, language: String): Result<Movie>

}
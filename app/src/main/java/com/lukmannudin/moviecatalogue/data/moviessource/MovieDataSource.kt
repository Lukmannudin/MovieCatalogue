package com.lukmannudin.moviecatalogue.data.moviessource

import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result

/**
 * Created by Lukmannudin on 09/05/21.
 */


interface MovieDataSource {

    suspend fun getPopularMovies(language: String, page: Int): Result<List<Movie>>

}
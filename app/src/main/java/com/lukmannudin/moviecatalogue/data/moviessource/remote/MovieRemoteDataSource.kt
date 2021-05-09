package com.lukmannudin.moviecatalogue.data.moviessource.remote

import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.moviessource.MovieDataSource
import com.lukmannudin.moviecatalogue.mapper.toMovies
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class MovieRemoteDataSource @Inject constructor(
    private val apiHelper: ApiHelper) : MovieDataSource {

    override suspend fun getPopularMovies(language: String, page: Int): Result<List<Movie>> {
        val movieRequest = apiHelper.getPopularMovies(language, page)
        return if (movieRequest.isSuccessful){
            val movieRequestResults = movieRequest.body()?.results
            Result.Success(movieRequestResults.toMovies())
        } else {
            Result.Error(Exception(movieRequest.message()))
        }
    }

}
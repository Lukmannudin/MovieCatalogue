package com.lukmannudin.moviecatalogue.data.moviessource.remote

import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.mapper.toMovieFromRemote
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class MovieRemoteDataSource @Inject constructor(
    private val apiHelper: ApiHelper
) {

    suspend fun getMovie(id: Int, language: String): Result<Movie> {
        val movieRequest = apiHelper.getMovie(id, language)
        return if (movieRequest.isSuccessful) {
            val movieRequestResults = movieRequest.body()
            if (movieRequestResults == null) {
                Result.Error(Exception("movie is empty"))
            } else {
                Result.Success(movieRequestResults.toMovieFromRemote())
            }
        } else {
            Result.Error(Exception(movieRequest.message()))
        }
    }
}
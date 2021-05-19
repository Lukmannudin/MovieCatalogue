package com.lukmannudin.moviecatalogue.data.moviessource.remote

import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.moviessource.MovieDataSource
import com.lukmannudin.moviecatalogue.mapper.toMovieFromRemote
import com.lukmannudin.moviecatalogue.mapper.toMoviesFromRemote
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
            Result.Success(movieRequestResults.toMoviesFromRemote())
        } else {
            Result.Error(Exception(movieRequest.message()))
        }
    }

    override suspend fun getMovie(id: Int, language: String): Result<Movie> {
        val movieRequest = apiHelper.getMovie(id, language)
        return if (movieRequest.isSuccessful){
            val movieRequestResults = movieRequest.body()
            if (movieRequestResults == null){
                Result.Error(Exception("movie is empty"))
            } else {
                Result.Success(movieRequestResults.toMovieFromRemote())
            }
        } else {
            Result.Error(Exception(movieRequest.message()))
        }
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        // do nothing
    }
}
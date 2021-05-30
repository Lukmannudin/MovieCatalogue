package com.lukmannudin.moviecatalogue.data.moviessource.remote

import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.moviessource.MovieDataSource
import com.lukmannudin.moviecatalogue.mapper.toMovieFromRemote
import com.lukmannudin.moviecatalogue.mapper.toMoviesFromRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class MovieRemoteDataSource @Inject constructor(
    private val apiHelper: ApiHelper
) : MovieDataSource {

    override suspend fun getMovie(id: Int, language: String): Result<Movie> {
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

    override suspend fun getPopularMovies(language: String, page: Int): Flow<PagingData<Movie>> {
        return flow {
            val movieRequest = apiHelper.getPopularMovies(language, page)
            emit(PagingData.from(movieRequest.body()?.results.toMoviesFromRemote()))
        }
    }


    override suspend fun getFavoriteMovies(pageSize: Int): Flow<PagingData<Movie>> {
        // currently api not available
        return flow {
            PagingData.empty<Movie>()
        }
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        // currently api not available
    }

    override suspend fun saveMovie(movie: Movie) {
        // currently api not available
    }

    override suspend fun updateFavorite(movie: Movie) {
        // currently api not available
    }

    override suspend fun updateMovie(movie: Movie) {
        // currently api not available
    }

}
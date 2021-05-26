package com.lukmannudin.moviecatalogue.data.moviessource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocalDataSource
import com.lukmannudin.moviecatalogue.mapper.toMovieFromRemote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class MovieRemoteDataSource @Inject constructor(
    private val apiHelper: ApiHelper
) {

    fun getPopularMovies(
        movieLocalDataSource: MovieLocalDataSource,
        language: String,
        pageSize: Int): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(pageSize)
        ) {
            PagedKeyedMoviePagingSource(
                movieLocalDataSource, language, apiHelper
            )
        }.flow
    }

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
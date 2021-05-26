package com.lukmannudin.moviecatalogue.api

import com.lukmannudin.moviecatalogue.data.entity.BaseResponse
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemote
import com.lukmannudin.moviecatalogue.data.tvshowssource.remote.TvShowRemote
import retrofit2.Response

/**
 * Created by Lukmannudin on 09/05/21.
 */


interface ApiHelper {
    suspend fun getPopularMovies(
        language: String,
        page: Int
    ) : Response<BaseResponse<List<MovieRemote>>>

    suspend fun getMovie(
        id: Int,
        language: String
    ) : Response<MovieRemote>

    suspend fun getPopularTvShows(
        language: String,
        page: Int
    ) : Response<BaseResponse<List<TvShowRemote>>>

    suspend fun getTvShow(
        id: Int,
        language: String
    ) : Response<TvShowRemote>
}
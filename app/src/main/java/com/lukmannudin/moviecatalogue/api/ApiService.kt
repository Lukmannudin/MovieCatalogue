package com.lukmannudin.moviecatalogue.api

import com.lukmannudin.moviecatalogue.data.entity.BaseResponse
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemote
import com.lukmannudin.moviecatalogue.data.tvshowssource.remote.TvShowRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Lukmannudin on 09/05/21.
 */


interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Response<BaseResponse<List<MovieRemote>>>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Response<BaseResponse<List<MovieRemote>>>

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ) : Response<MovieRemote>

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<BaseResponse<List<TvShowRemote>>>

    @GET("tv/{tv_id}")
    suspend fun getTvShow(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ) : Response<TvShowRemote>

    @GET("movie/latest")
    suspend fun getLatestMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ) : Response<MovieRemote>
}
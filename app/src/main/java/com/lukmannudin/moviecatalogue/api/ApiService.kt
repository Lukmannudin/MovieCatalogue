package com.lukmannudin.moviecatalogue.api

import com.lukmannudin.moviecatalogue.data.BaseResponse
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemote
import retrofit2.Response
import retrofit2.http.GET
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
}
package com.lukmannudin.moviecatalogue.api

import com.lukmannudin.moviecatalogue.data.BaseResponse
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemote
import retrofit2.Response

/**
 * Created by Lukmannudin on 09/05/21.
 */


interface ApiHelper {
    suspend fun getPopularMovies(
        language: String,
        page: Int
    ) : Response<BaseResponse<List<MovieRemote>>>
}
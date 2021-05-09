package com.lukmannudin.moviecatalogue.api

import com.lukmannudin.moviecatalogue.BuildConfig
import com.lukmannudin.moviecatalogue.data.BaseResponse
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemote
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    private val apiKey = BuildConfig.MOVIE_DB_API_KEY

    override suspend fun getPopularMovies(
        language: String,
        page: Int
    ): Response<BaseResponse<List<MovieRemote>>> {
        return apiService.getPopularMovies(apiKey, language, page)
    }
}
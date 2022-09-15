package com.lukmannudin.moviecatalogue.api

import com.lukmannudin.moviecatalogue.BuildConfig
import com.lukmannudin.moviecatalogue.data.entity.BaseResponse
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemote
import com.lukmannudin.moviecatalogue.data.tvshowssource.remote.TvShowRemote
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

    override suspend fun getPopularTvShows(
        language: String,
        page: Int
    ): Response<BaseResponse<List<TvShowRemote>>> {
        return apiService.getPopularTvShows(apiKey, language, page)
    }

    override suspend fun getMovie(id: Int, language: String): Response<MovieRemote> {
        return apiService.getMovie(id, apiKey, language)
    }

    override suspend fun getTvShow(id: Int, language: String): Response<TvShowRemote> {
        return apiService.getTvShow(id, apiKey, language)
    }

    override suspend fun getLatestMovie(language: String): Response<MovieRemote> {
        return apiService.getLatestMovie(apiKey, language)
    }

    override suspend fun getNowPlayingMovies(
        language: String,
        page: Int
    ): Response<BaseResponse<List<MovieRemote>>> {
        return apiService.getNowPlaying(apiKey, language, page)
    }
}
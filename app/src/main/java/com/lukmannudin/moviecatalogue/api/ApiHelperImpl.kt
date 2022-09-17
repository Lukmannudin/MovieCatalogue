package com.lukmannudin.moviecatalogue.api

import com.lukmannudin.moviecatalogue.data.entity.BaseResponse
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemote
import com.lukmannudin.moviecatalogue.data.tvshowssource.remote.TvShowRemote
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getPopularMovies(
        language: String,
        page: Int
    ): Response<BaseResponse<List<MovieRemote>>> {
        return apiService.getPopularMovies(language, page)
    }

    override suspend fun getPopularTvShows(
        language: String,
        page: Int
    ): Response<BaseResponse<List<TvShowRemote>>> {
        return apiService.getPopularTvShows(language, page)
    }

    override suspend fun getMovie(id: Int, language: String): Response<MovieRemote> {
        return apiService.getMovie(id, language)
    }

    override suspend fun getTvShow(id: Int, language: String): Response<TvShowRemote> {
        return apiService.getTvShow(id, language)
    }

    override suspend fun getLatestMovie(language: String): Response<MovieRemote> {
        return apiService.getLatestMovie(language)
    }

    override suspend fun getNowPlayingMovies(
        language: String,
        page: Int
    ): Response<BaseResponse<List<MovieRemote>>> {
        return apiService.getNowPlaying(language, page)
    }

    override suspend fun getOnAirTvShows(
        language: String,
        page: Int
    ): Response<BaseResponse<List<TvShowRemote>>> {
        return apiService.getOnAirTvShows(language)
    }

    override suspend fun getLatestTvShow(language: String): Response<TvShowRemote> {
        return apiService.getLatestTvShow(language)
    }


}
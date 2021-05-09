package com.lukmannudin.moviecatalogue.data.tvshowssource.remote

import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowDataSource
import com.lukmannudin.moviecatalogue.mapper.toTvShow
import com.lukmannudin.moviecatalogue.mapper.toTvShows
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class TvShowRemoteDataSource @Inject constructor(
    private val apiHelper: ApiHelper) : TvShowDataSource {

    override suspend fun getPopularTvShows(language: String, page: Int): Result<List<TvShow>> {
        val tvShowsRequest = apiHelper.getPopularTvShows(language, page)
        return if (tvShowsRequest.isSuccessful){
            val tvShowResults = tvShowsRequest.body()?.results
            Result.Success(tvShowResults.toTvShows())
        } else {
            Result.Error(Exception(tvShowsRequest.message()))
        }
    }

    override suspend fun getTvShow(id: Int, language: String): Result<TvShow> {
        val tvShowRequest = apiHelper.getTvShow(id, language)
        return if (tvShowRequest.isSuccessful){
            val tvShowResult = tvShowRequest.body()
            if (tvShowResult == null){
                Result.Error(Exception("tv show is empty"))
            } else {
                Result.Success(tvShowResult.toTvShow())
            }
        } else {
            Result.Error(Exception(tvShowRequest.message()))
        }
    }
}
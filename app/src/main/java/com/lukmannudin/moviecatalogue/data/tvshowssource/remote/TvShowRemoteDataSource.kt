package com.lukmannudin.moviecatalogue.data.tvshowssource.remote

import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowDataSource
import com.lukmannudin.moviecatalogue.mapper.toTvShow
import com.lukmannudin.moviecatalogue.mapper.toTvShows
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class TvShowRemoteDataSource @Inject constructor(
    private val apiHelper: ApiHelper
) : TvShowDataSource {

    override suspend fun getPopularTvShows(
        language: String,
        page: Int
    ): Flow<PagingData<TvShow>> {
        return flow {
            val tvShowsRequest = apiHelper.getPopularTvShows(language, page)
            emit(PagingData.from(tvShowsRequest.body()?.results.toTvShows()))
        }
    }

    override suspend fun getTvShow(id: Int, language: String): Result<TvShow> {
        val tvShowRequest = apiHelper.getTvShow(id, language)
        return if (tvShowRequest.isSuccessful) {
            val tvShowResult = tvShowRequest.body()
            if (tvShowResult == null) {
                Result.Error(Exception("tv show is empty"))
            } else {
                Result.Success(tvShowResult.toTvShow())
            }
        } else {
            Result.Error(Exception(tvShowRequest.message()))
        }
    }

    override suspend fun getFavoriteTvShows(pageSize: Int): Flow<PagingData<TvShow>> {
        // currently api not available
        return flow {
            PagingData.empty<Movie>()
        }
    }

    override suspend fun saveTvShows(tvShows: List<TvShow>) {
        // currently api not available
    }

    override suspend fun saveTvShow(tvShow: TvShow) {
        // currently api not available
    }

    override suspend fun updateFavorite(tvShow: TvShow) {
        // currently api not available
    }

    override suspend fun updateTvShow(tvShow: TvShow) {
        // currently api not available
    }
}
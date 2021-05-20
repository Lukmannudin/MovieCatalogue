package com.lukmannudin.moviecatalogue.data.tvshowssource

import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.TvShow
import com.lukmannudin.moviecatalogue.utils.Constant

/**
 * Created by Lukmannudin on 11/05/21.
 */

class FakeTvShowDataSource : TvShowDataSource {
    override suspend fun getPopularTvShows(language: String, page: Int): Result<List<TvShow>> {
        return when {
            language != Constant.DEFAULT_LANGUAGE -> Result.Error(Exception(""))
            page < 1 -> Result.Error(Exception(""))
            else -> {
                return Result.Success(emptyList())
            }
        }
    }

    override suspend fun getTvShow(id: Int, language: String): Result<TvShow> {
        return when {
            id < 0 -> Result.Error(Exception(""))
            language != Constant.DEFAULT_LANGUAGE -> Result.Error(Exception(""))
            else -> {
                return Result.Success(dummyTvShow)
            }
        }
    }

    companion object {
        val dummyTvShow = TvShow(
            1,
            "title",
            "overview",
            "releaseDate",
            0f,
            "posterPath"
        )
    }
}
package com.lukmannudin.moviecatalogue.data.tvshowssource

import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.TvShow
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class TvShowRepositoryImpl @Inject constructor(
    private val tvShowRemoteDataSource: TvShowDataSource
) : TvShowRepository {

    override suspend fun getPopularTvShows(language: String, page: Int): Result<List<TvShow>> {
        return tvShowRemoteDataSource.getPopularTvShows(language, page)
    }

    override suspend fun getTvShow(id: Int, language: String): Result<TvShow> {
        return tvShowRemoteDataSource.getTvShow(id, language)
    }

}
package com.lukmannudin.moviecatalogue.data.tvshowssource

import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.TvShow
import com.lukmannudin.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Lukmannudin on 09/05/21.
 */


class TvShowRepositoryImpl @Inject constructor(
    private val tvShowRemoteDataSource: TvShowDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : TvShowRepository {

    override suspend fun getPopularTvShows(language: String, page: Int): Result<List<TvShow>> {
        EspressoIdlingResource.increment()
        return withContext(ioDispatcher){
            EspressoIdlingResource.decrement()
            tvShowRemoteDataSource.getPopularTvShows(language, page)
        }
    }

    override suspend fun getTvShow(id: Int, language: String): Result<TvShow> {
        EspressoIdlingResource.increment()
        return withContext(ioDispatcher){
            EspressoIdlingResource.decrement()
            tvShowRemoteDataSource.getTvShow(id, language)
        }
    }

}
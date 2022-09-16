package com.lukmannudin.moviecatalogue.data.tvshowssource

import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import kotlinx.coroutines.flow.Flow
import com.lukmannudin.moviecatalogue.data.entity.Result

/**
 * Created by Lukmannudin on 09/05/21.
 */


interface TvShowDataSource {

    suspend fun getPopularTvShows(language: String, page: Int): Flow<PagingData<TvShow>>

    suspend fun getTvShow(id: Int, language: String): Result<TvShow>
    
    suspend fun getFavoriteTvShows(pageSize: Int): Flow<PagingData<TvShow>>

    suspend fun saveTvShows(tvShows: List<TvShow>)

    suspend fun saveTvShow(tvShow: TvShow)

    suspend fun updateFavorite(tvShow: TvShow)

    suspend fun updateTvShow(tvShow: TvShow)

    suspend fun getOnAirTvShows(language: String, page: Int): Flow<PagingData<TvShow>>

    suspend fun getLatestTvShow(language: String): Result<TvShow>

}
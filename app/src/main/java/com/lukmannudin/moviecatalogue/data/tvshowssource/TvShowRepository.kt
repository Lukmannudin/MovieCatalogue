package com.lukmannudin.moviecatalogue.data.tvshowssource

import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import kotlinx.coroutines.flow.Flow

/**
 * Created by Lukmannudin on 09/05/21.
 */


interface TvShowRepository {

    suspend fun getPopularTvShows(): Flow<PagingData<TvShow>>

    suspend fun getTvShow(id: Int, language: String): Flow<Result<TvShow>>

    suspend fun updateFavorite(tvShow: TvShow)

    suspend fun getFavoriteTvShows(pageSize: Int): Flow<PagingData<TvShow>>

}
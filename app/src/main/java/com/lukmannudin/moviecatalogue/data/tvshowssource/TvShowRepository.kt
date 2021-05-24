package com.lukmannudin.moviecatalogue.data.tvshowssource

import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.local.TvShowLocal
import kotlinx.coroutines.flow.Flow

/**
 * Created by Lukmannudin on 09/05/21.
 */


interface TvShowRepository {

    suspend fun getPopularTvShows(language: String, pageSize: Int): Flow<PagingData<TvShow>>

    suspend fun getTvShow(id: Int, language: String): Flow<Result<TvShow>>

}
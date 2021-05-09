package com.lukmannudin.moviecatalogue.data.tvshowssource

import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.TvShow

/**
 * Created by Lukmannudin on 09/05/21.
 */


interface TvShowRepository {

    suspend fun getPopularTvShows(language: String, page: Int): Result<List<TvShow>>

    suspend fun getTvShow(id: Int, language: String): Result<TvShow>

}
package com.lukmannudin.moviecatalogue.data.tvshowssource.local

import androidx.paging.PagingSource
import androidx.room.*
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocal

/**
 * Created by Lukmannudin on 19/05/21.
 */

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShow: TvShowLocal)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTvShows(tvShows: List<TvShowLocal>)

    @Query("SELECT * FROM tvshow ORDER BY release_date DESC")
    fun getTvShows(): PagingSource<Int, TvShowLocal>

    @Query("SELECT * FROM tvshow WHERE id = :tvShowId")
    fun getTvShow(tvShowId: Int): TvShowLocal

    @Query("SELECT * FROM tvshow WHERE is_favorite = 1")
    fun getFavoriteTvShows(): PagingSource<Int, TvShowLocal>

    @Query("UPDATE tvshow SET is_favorite = :isFavorite WHERE id = :tvShowId")
    suspend fun updateFavorite(tvShowId: Int, isFavorite: Boolean)

    @Update
    suspend fun updateTvShow(tvShowLocal: TvShowLocal)

    @Query("DELETE FROM tvshow WHERE id = :tvShowId")
    fun deleteTvShowById(tvShowId: Int)

    @Query("DELETE FROM tvshow")
    suspend fun clearCache()
}
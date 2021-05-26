package com.lukmannudin.moviecatalogue.data.tvshowssource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Lukmannudin on 19/05/21.
 */

@Dao
interface TvShowDao {

    @Query("SELECT * FROM tvshow ORDER BY releaseDate DESC")
    fun getTvShows(): PagingSource<Int, TvShowLocal>

    @Query("SELECT * FROM tvshow WHERE id = :tvShowId")
    fun getTvShow(tvShowId: Int): TvShowLocal

    @Query("DELETE FROM tvshow WHERE id = :tvShowId")
    fun deleteTvShowById(tvShowId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTvShows(tvShows: List<TvShowLocal>)

    @Query("DELETE FROM tvshow")
    suspend fun clearCache()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShow: TvShowLocal)
}
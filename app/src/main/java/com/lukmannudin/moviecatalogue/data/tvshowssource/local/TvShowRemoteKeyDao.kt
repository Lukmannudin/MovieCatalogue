package com.lukmannudin.moviecatalogue.data.tvshowssource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieRemoteKey

/**
 * Created by Lukmannudin on 21/05/21.
 */
@Dao
interface TvShowRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: TvShowRemoteKey)

    @Query("SELECT * FROM tv_remote_keys LIMIT 1")
    suspend fun remote_key(): TvShowRemoteKey

    @Query("UPDATE tv_remote_keys SET tvshow_remote_key_next_page = :page WHERE id = 1 ")
    suspend fun updateCurrentMovieNextPage(page: Int)

    @Query("DELETE FROM tv_remote_keys")
    suspend fun clearCache()
}
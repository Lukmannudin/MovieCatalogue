package com.lukmannudin.moviecatalogue.data.tvshowssource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Lukmannudin on 21/05/21.
 */
@Dao
interface TvShowRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: TvShowRemoteKey)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKeys: List<TvShowRemoteKey>)

    @Query("SELECT * FROM tv_remote_keys WHERE page_index = :pageIndex")
    suspend fun remoteKeyById(pageIndex: Int): TvShowRemoteKey

    @Query("DELETE FROM tv_remote_keys WHERE page_index = :pageIndex")
    suspend fun deleteById(pageIndex: Int)

    @Query("DELETE FROM tv_remote_keys")
    suspend fun clearCache()
}
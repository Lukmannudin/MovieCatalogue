package com.lukmannudin.moviecatalogue.data.moviessource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Lukmannudin on 21/05/21.
 */
@Dao
interface MovieRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: MovieRemoteKey)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKeys: List<MovieRemoteKey>)

    @Query("SELECT * FROM movie_remote_keys WHERE page_index = :pageIndex")
    suspend fun remoteKeyById(pageIndex: Int): MovieRemoteKey

    @Query("DELETE FROM movie_remote_keys WHERE page_index = :pageIndex")
    suspend fun deleteById(pageIndex: Int)

    @Query("DELETE FROM movie_remote_keys")
    suspend fun clearCache()
}
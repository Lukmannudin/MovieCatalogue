package com.lukmannudin.moviecatalogue.data.moviessource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Lukmannudin on 21/05/21.
 */

@Entity(tableName = "movie_remote_keys")
data class MovieRemoteKey(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Int,

    @ColumnInfo(name = "next_page")
    val nextPage: Int?,
)
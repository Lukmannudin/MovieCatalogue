package com.lukmannudin.moviecatalogue.data.moviessource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Lukmannudin on 21/05/21.
 */

@Entity(tableName = "movie_remote_keys")
data class MovieRemoteKey(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,

    @ColumnInfo(name = "movie_remote_key_next_page")
    val movieNextPage: Int?,
)
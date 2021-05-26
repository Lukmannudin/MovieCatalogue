package com.lukmannudin.moviecatalogue.data.tvshowssource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Lukmannudin on 21/05/21.
 */

@Entity(tableName = "tv_remote_keys")
data class TvShowRemoteKey(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,

    @ColumnInfo(name = "tvshow_remote_key_next_page")
    val tvShowNextPage: Int?,
)
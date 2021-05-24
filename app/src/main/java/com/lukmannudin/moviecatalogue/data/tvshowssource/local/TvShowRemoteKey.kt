package com.lukmannudin.moviecatalogue.data.tvshowssource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Lukmannudin on 21/05/21.
 */

@Entity(tableName = "tv_remote_keys")
data class TvShowRemoteKey(
    @PrimaryKey
    @ColumnInfo(name = "page_index")
    val pageIndex: Int,

    @ColumnInfo(name = "next_page")
    val nextPage: Int?,
)
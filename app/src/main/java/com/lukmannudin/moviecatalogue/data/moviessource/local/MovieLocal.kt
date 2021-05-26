package com.lukmannudin.moviecatalogue.data.moviessource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Lukmannudin on 09/05/21.
 */

@Entity(tableName = "movies")
data class MovieLocal(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: Long?,

    @ColumnInfo(name = "user_score")
    val userScore: Float,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)
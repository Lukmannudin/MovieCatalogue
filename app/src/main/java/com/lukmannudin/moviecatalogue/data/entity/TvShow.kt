package com.lukmannudin.moviecatalogue.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class TvShow(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: Date?,
    val userScore: Float,
    val posterPath: String,
    var isFavorite: Boolean = false
) : Parcelable
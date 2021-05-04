package com.lukmannudin.moviecatalogue.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShow(
    val id: String,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val userScore: Float,
    val posterPath: String
) : Parcelable
package com.lukmannudin.moviecatalogue.data.entity

import android.os.Parcelable
import com.lukmannudin.moviecatalogue.data.moviessource.local.Genre
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: Date?,
    val userScore: Float,
    val posterPath: String,
    var isFavorite: Boolean = false,
    var genres: List<Genre>? = null,
    var popularity: Float? = null,
) : Parcelable
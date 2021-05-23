package com.lukmannudin.moviecatalogue.data

import android.os.Parcelable
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
    val page: Int
) : Parcelable
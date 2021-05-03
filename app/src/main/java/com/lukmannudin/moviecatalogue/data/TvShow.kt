package com.lukmannudin.moviecatalogue.data

data class TvShow(
    val id: String,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val userScore: Float,
    val posterPath: String
)
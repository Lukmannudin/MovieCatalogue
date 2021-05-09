package com.lukmannudin.moviecatalogue.data.moviessource.remote

import com.google.gson.annotations.SerializedName

/**
 * Created by Lukmannudin on 09/05/21.
 */


data class MovieRemote (
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("original_title")
    val title: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("vote_average")
    val userScore: Float? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null
)
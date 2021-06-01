package com.lukmannudin.moviecatalogue.data.moviessource.remote

import com.google.gson.annotations.SerializedName
import com.lukmannudin.moviecatalogue.data.moviessource.local.Genre

/**
 * Created by Lukmannudin on 09/05/21.
 */


data class MovieRemote (
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("vote_average")
    val userScore: Float? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("genres")
    val genres: List<Genre>? = null,

    @SerializedName("popularity")
    val popularity: Float? = null
)
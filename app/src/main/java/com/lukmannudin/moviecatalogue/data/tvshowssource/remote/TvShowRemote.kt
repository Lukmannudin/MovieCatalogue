package com.lukmannudin.moviecatalogue.data.tvshowssource.remote

import com.google.gson.annotations.SerializedName

/**
 * Created by Lukmannudin on 09/05/21.
 */


data class TvShowRemote(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("original_name")
    val original_name: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("first_air_date")
    val releaseDate: String? = null,

    @SerializedName("vote_average")
    val userScore: Float? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null
)
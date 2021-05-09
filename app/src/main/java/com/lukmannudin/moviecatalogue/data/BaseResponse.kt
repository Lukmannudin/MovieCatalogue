package com.lukmannudin.moviecatalogue.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Lukmannudin on 09/05/21.
 */


data class BaseResponse<T>(
    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("results")
    val results: T? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("total_results")
    val totalResults: Int? = null
)
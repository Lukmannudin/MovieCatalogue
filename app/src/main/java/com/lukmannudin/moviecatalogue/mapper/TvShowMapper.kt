package com.lukmannudin.moviecatalogue.mapper

import com.lukmannudin.moviecatalogue.data.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.remote.TvShowRemote
import com.lukmannudin.moviecatalogue.mapper.mapperhelper.Mapper
import com.lukmannudin.moviecatalogue.mapper.mapperhelper.NullableInputListMapper
import com.lukmannudin.moviecatalogue.mapper.mapperhelper.NullableInputListMapperImpl

/**
 * Created by Lukmannudin on 09/05/21.
 */

private val tvShowRemoteToTvShow : Mapper<TvShowRemote, TvShow> =
    object : Mapper<TvShowRemote, TvShow> {
        override fun map(input: TvShowRemote): TvShow {
            val basePosterPath = "https://image.tmdb.org/t/p/w500"
            return TvShow(
                input.id ?: -1,
                input.name ?: input.original_name ?: "title not defined",
                input.overview ?: "overview not defined",
                input.releaseDate ?: "release date not defined",
                input.userScore ?: 0.0f,
                basePosterPath + input.posterPath
            )
        }
    }

private val tvShowsRemoteToTvShows : NullableInputListMapper<TvShowRemote, TvShow> =
    object : NullableInputListMapper<TvShowRemote, TvShow> {
        override fun map(input: List<TvShowRemote>?): List<TvShow> {
            return NullableInputListMapperImpl(tvShowRemoteToTvShow).map(input)
        }
    }

fun List<TvShowRemote>?.toTvShows(): List<TvShow> {
    return if (this == null){
        emptyList()
    } else {
        tvShowsRemoteToTvShows.map(this)
    }
}

fun TvShowRemote.toTvShow(): TvShow {
    return tvShowRemoteToTvShow.map(this)
}
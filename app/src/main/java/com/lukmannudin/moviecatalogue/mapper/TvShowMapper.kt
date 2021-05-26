package com.lukmannudin.moviecatalogue.mapper

import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.local.TvShowLocal
import com.lukmannudin.moviecatalogue.data.tvshowssource.remote.TvShowRemote
import com.lukmannudin.moviecatalogue.mapper.mapperhelper.*
import com.lukmannudin.moviecatalogue.utils.Converters.toDate
import com.lukmannudin.moviecatalogue.utils.Converters.toLong

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
                input.releaseDate?.toDate(),
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

private val tvShowLocalToTvShowMapper: Mapper<TvShowLocal, TvShow> =
    object : Mapper<TvShowLocal, TvShow> {
        override fun map(input: TvShowLocal): TvShow {
            return TvShow(
                input.id,
                input.title,
                input.overview,
                input.releaseDate.toDate(),
                input.userScore,
                input.posterPath,
                input.isFavorite
            )
        }
    }

private val tvShowsLocalToTvShowsMapper: ListMapper<TvShowLocal, TvShow> =
    object : ListMapper<TvShowLocal, TvShow> {
        override fun map(input: List<TvShowLocal>): List<TvShow> {
            return ListMapperImpl(tvShowLocalToTvShowMapper).map(input)
        }
    }

private val tvShowToLocalMapper: Mapper<TvShow, TvShowLocal> =
    object : Mapper<TvShow, TvShowLocal> {
        override fun map(input: TvShow): TvShowLocal {
            return TvShowLocal(
                input.id,
                input.title,
                input.overview,
                input.releaseDate.toLong(),
                input.userScore,
                input.posterPath,
                input.isFavorite
            )
        }
    }

private val tvShowsToLocalMapper: ListMapper<TvShow, TvShowLocal> =
    object : ListMapper<TvShow, TvShowLocal> {
        override fun map(input: List<TvShow>): List<TvShowLocal> {
            return ListMapperImpl(tvShowToLocalMapper).map(input)
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

fun TvShowLocal.toTvShow(): TvShow {
    return tvShowLocalToTvShowMapper.map(this)
}

fun List<TvShowLocal>.toTvShowsFromLocal(): List<TvShow> {
    return tvShowsLocalToTvShowsMapper.map(this)
}

fun TvShow.toTvShowLocal(): TvShowLocal {
    return tvShowToLocalMapper.map(this)
}

fun List<TvShow>.toTvShowsLocal(): List<TvShowLocal> {
    return tvShowsToLocalMapper.map(this)
}

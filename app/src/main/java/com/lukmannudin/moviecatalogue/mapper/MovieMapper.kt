package com.lukmannudin.moviecatalogue.mapper

import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemote
import com.lukmannudin.moviecatalogue.mapper.mapperhelper.Mapper
import com.lukmannudin.moviecatalogue.mapper.mapperhelper.NullableInputListMapper
import com.lukmannudin.moviecatalogue.mapper.mapperhelper.NullableInputListMapperImpl

/**
 * Created by Lukmannudin on 09/05/21.
 */

private val movieRemoteToMovieMapper : Mapper<MovieRemote, Movie> =
    object : Mapper<MovieRemote, Movie> {
        override fun map(input: MovieRemote): Movie {
            val basePosterPath = "https://image.tmdb.org/t/p/w500"
            return Movie(
                input.id ?: -1,
                input.title ?: input.originalTitle ?: "title not defined",
                input.overview ?: "overview not defined",
                input.releaseDate ?: "release date not defined",
                input.userScore ?: 0.0f,
                basePosterPath + input.posterPath
            )
        }
    }

private val moviesRemoteToMoviesMapper : NullableInputListMapper<MovieRemote, Movie> =
    object : NullableInputListMapper<MovieRemote, Movie> {
        override fun map(input: List<MovieRemote>?): List<Movie> {
            return NullableInputListMapperImpl(movieRemoteToMovieMapper).map(input)
        }
    }

fun List<MovieRemote>?.toMovies(): List<Movie> {
    return if (this == null){
        emptyList()
    } else {
        moviesRemoteToMoviesMapper.map(this)
    }
}

fun MovieRemote.toMovie(): Movie {
    return movieRemoteToMovieMapper.map(this)
}
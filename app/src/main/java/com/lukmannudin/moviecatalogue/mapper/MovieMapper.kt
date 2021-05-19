package com.lukmannudin.moviecatalogue.mapper

import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocal
import com.lukmannudin.moviecatalogue.data.moviessource.remote.MovieRemote
import com.lukmannudin.moviecatalogue.mapper.mapperhelper.*
import com.lukmannudin.moviecatalogue.utils.Converters.toDate
import com.lukmannudin.moviecatalogue.utils.Converters.toLong

/**
 * Created by Lukmannudin on 09/05/21.
 */

private val movieRemoteToMovieMapper: Mapper<MovieRemote, Movie> =
    object : Mapper<MovieRemote, Movie> {
        override fun map(input: MovieRemote): Movie {
            val basePosterPath = "https://image.tmdb.org/t/p/w500"
            return Movie(
                input.id ?: -1,
                input.title ?: input.originalTitle ?: "title not defined",
                input.overview ?: "overview not defined",
                input.releaseDate?.toDate(),
                input.userScore ?: 0.0f,
                basePosterPath + input.posterPath
            )
        }
    }

private val moviesRemoteToMoviesMapper: NullableInputListMapper<MovieRemote, Movie> =
    object : NullableInputListMapper<MovieRemote, Movie> {
        override fun map(input: List<MovieRemote>?): List<Movie> {
            return NullableInputListMapperImpl(movieRemoteToMovieMapper).map(input)
        }
    }

private val movieLocalToMovieMapper: Mapper<MovieLocal, Movie> =
    object : Mapper<MovieLocal, Movie> {
        override fun map(input: MovieLocal): Movie {
            return Movie(
                input.id,
                input.title,
                input.overview,
                input.releaseDate.toDate(),
                input.userScore,
                input.posterPath
            )
        }
    }

private val moviesLocalToMoviesMapper: ListMapper<MovieLocal, Movie> =
    object : ListMapper<MovieLocal, Movie> {
        override fun map(input: List<MovieLocal>): List<Movie> {
            return ListMapperImpl(movieLocalToMovieMapper).map(input)
        }
    }

private val movieToLocalMapper: Mapper<Movie, MovieLocal> =
    object : Mapper<Movie, MovieLocal> {
        override fun map(input: Movie): MovieLocal {
            return MovieLocal(
                input.id,
                input.title,
                input.overview,
                input.releaseDate.toLong(),
                input.userScore,
                input.posterPath
            )
        }
    }

private val moviesToLocalMapper: ListMapper<Movie, MovieLocal> =
    object : ListMapper<Movie, MovieLocal> {
        override fun map(input: List<Movie>): List<MovieLocal> {
            return ListMapperImpl(movieToLocalMapper).map(input)
        }
    }

fun List<MovieRemote>?.toMoviesFromRemote(): List<Movie> {
    return if (this == null) {
        emptyList()
    } else {
        moviesRemoteToMoviesMapper.map(this)
    }
}

fun MovieRemote.toMovieFromRemote(): Movie {
    return movieRemoteToMovieMapper.map(this)
}

fun List<MovieLocal>.toMoviesFromLocal(): List<Movie>{
    return moviesLocalToMoviesMapper.map(this)
}

fun MovieLocal.toMovieFromLocal(): Movie {
    return movieLocalToMovieMapper.map(this)
}

fun List<Movie>.toMoviesLocal(): List<MovieLocal> {
    return moviesToLocalMapper.map(this)
}

fun Movie.toMovieLocal(): MovieLocal {
    return movieToLocalMapper.map(this)
}
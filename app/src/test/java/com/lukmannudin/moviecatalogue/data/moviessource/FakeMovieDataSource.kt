package com.lukmannudin.moviecatalogue.data.moviessource

import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.utils.Constant

/**
 * Created by Lukmannudin on 10/05/21.
 */


class FakeMovieDataSource : MovieDataSource {
    override suspend fun getPopularMovies(language: String, page: Int): Result<List<Movie>> {
        return when {
            language != Constant.LANGUAGE -> Result.Error(Exception(""))
            page < 1 -> Result.Error(Exception(""))
            else -> {
                return Result.Success(emptyList())
            }
        }
    }

    override suspend fun getMovie(id: Int, language: String): Result<Movie> {
        return when {
            id < 0 -> Result.Error(Exception(""))
            language != Constant.LANGUAGE -> Result.Error(Exception(""))
            else -> {
                return Result.Success(dummyMovie)
            }
        }
    }

    companion object {
        val dummyMovie = Movie(
            1,
            "title",
            "overview",
            "releaseDate",
            0f,
            "posterPath"
        )
    }
}
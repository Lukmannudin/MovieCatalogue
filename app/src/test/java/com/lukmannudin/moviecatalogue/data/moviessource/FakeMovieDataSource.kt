package com.lukmannudin.moviecatalogue.data.moviessource

import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.utils.PagingCatalogueConfig
import java.util.*

/**
 * Created by Lukmannudin on 10/05/21.
 */


class FakeMovieDataSource : MovieDataSource {
    override suspend fun getPopularMovies(language: String, page: Int): Result<List<Movie>> {
        return when {
            language != PagingCatalogueConfig.DEFAULT_LANGUAGE -> Result.Error(Exception(""))
            page < 1 -> Result.Error(Exception(""))
            else -> {
                return Result.Success(emptyList())
            }
        }
    }

    override suspend fun getMovie(id: Int, language: String): Result<Movie> {
        return when {
            id < 0 -> Result.Error(Exception(""))
            language != PagingCatalogueConfig.DEFAULT_LANGUAGE -> Result.Error(Exception(""))
            else -> {
                return Result.Success(dummyMovie)
            }
        }
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        // do nothing
    }

    companion object {
        val dummyMovie = Movie(
            1,
            "title",
            "overview",
            Date(),
            0f,
            "posterPath"
        )
    }
}
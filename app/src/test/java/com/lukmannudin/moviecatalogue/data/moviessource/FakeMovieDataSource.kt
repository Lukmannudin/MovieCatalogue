package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.DummiesTest
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Lukmannudin on 10/05/21.
 */


class FakeMovieDataSource : MovieDataSource {
    override suspend fun getPopularMovies(language: String, page: Int): Flow<PagingData<Movie>> {
        return flow {
            emit(PagingData.from(listOf(DummiesTest.dummyMovie)))
        }
    }

    override suspend fun getFavoriteMovies(pageSize: Int): Flow<PagingData<Movie>> {
        return flow {
            emit(PagingData.from(listOf(DummiesTest.dummyMovie)))
        }
    }

    override suspend fun getMovie(id: Int, language: String): Result<Movie> {
        return if (id != DummiesTest.dummyMovie.id) {
            Result.Error(Exception(DummiesTest.errorMessage))
        } else {
            Result.Success(DummiesTest.dummyMovie)
        }
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        // do nothing
    }

    override suspend fun saveMovie(movie: Movie) {
        // do nothing
    }

    override suspend fun updateFavorite(movie: Movie) {
        // do nothing
    }

    override suspend fun updateMovie(movie: Movie) {
        // do nothing
    }
}
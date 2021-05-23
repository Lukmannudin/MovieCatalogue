package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.*
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.mapper.toMovieFromLocal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
/**
 * Created by Lukmannudin on 09/05/21.
 */


@ExperimentalPagingApi
class MovieRepositoryImpl @ExperimentalPagingApi
@Inject constructor(
    private val database: MovieCatalogueDatabase,
    private val moviesMediator: MoviesMediator,
    private val coroutineDispatcher: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getPopularMovies(
        language: String,
        pageSize: Int
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize),
            remoteMediator = moviesMediator
        ) {
            database.movieDao().getMovies()
        }.flow.map { pagingData ->
            pagingData.map {
                it.toMovieFromLocal()
            }
        }
    }

    override suspend fun getMovie(id: Int, language: String): Result<Movie> {
       return Result.Error(Exception())
    }

}
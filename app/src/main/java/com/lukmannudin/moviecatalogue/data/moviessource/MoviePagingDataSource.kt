package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.*
import com.lukmannudin.moviecatalogue.MovieCatalogueDatabase
import com.lukmannudin.moviecatalogue.data.PagingCatalogueConfig
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.mapper.toMovieFromLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
class MoviePagingDataSource @Inject constructor(
    val database: MovieCatalogueDatabase,
    val moviesMediator: MoviesMediator
) : PagingDataSource<Movie> {

    override fun getPopularItems(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(PagingCatalogueConfig.DEFAULT_PAGE_SIZE),
            remoteMediator = moviesMediator
        ) {
            database.movieDao().getMovies()
        }.flow.map { pagingData ->
            pagingData.map { tvShowLocal ->
                tvShowLocal.toMovieFromLocal()
            }
        }
    }
}
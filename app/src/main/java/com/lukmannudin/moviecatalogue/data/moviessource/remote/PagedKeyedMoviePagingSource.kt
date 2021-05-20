package com.lukmannudin.moviecatalogue.data.moviessource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocalDataSource
import com.lukmannudin.moviecatalogue.mapper.toMoviesFromRemote
import com.lukmannudin.moviecatalogue.utils.Constant
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Lukmannudin on 20/05/21.
 */
class PagedKeyedMoviePagingSource(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val language: String,
    private val apiHelper: ApiHelper
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: Constant.DEFAULT_PAGE_INDEX

        return try {
            val response = apiHelper.getPopularMovies(
                language, page
            ).body()?.results.toMoviesFromRemote()

            // save to local database
            movieLocalDataSource.saveMovies(response)

            LoadResult.Page(
                response,
                prevKey = if (page == Constant.DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}
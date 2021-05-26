package com.lukmannudin.moviecatalogue.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepository
import com.lukmannudin.moviecatalogue.utils.Constant.DEFAULT_LANGUAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Lukmannudin on 5/3/21.
 */

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    suspend fun movies(): Flow<PagingData<Movie>> {
        return movieRepository.getPopularMovies(DEFAULT_LANGUAGE, 1).cachedIn(viewModelScope)
    }

    fun updateFavorite(movie: Movie){
        viewModelScope.launch {
            movieRepository.updateFavorite(movie)
        }
    }
}
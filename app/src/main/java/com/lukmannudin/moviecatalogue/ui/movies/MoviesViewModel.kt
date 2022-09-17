package com.lukmannudin.moviecatalogue.ui.movies

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepository
import com.lukmannudin.moviecatalogue.utils.PagingCatalogueConfig.DEFAULT_LANGUAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * Created by Lukmannudin on 5/3/21.
 */

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _latestMovie = MutableLiveData<Movie>()
    val latestMovie: LiveData<Movie> = _latestMovie

    private val _movies = MutableLiveData<PagedList<Movie>>()
    val movies: LiveData<PagedList<Movie>> = _movies

    suspend fun popularMovies(): Flow<PagingData<Movie>> {
        return movieRepository.getPopularMovies().cachedIn(viewModelScope)
    }

    suspend fun nowPlayingMovies(): Flow<PagingData<Movie>> {
        return movieRepository.getNowPlayingMovies().cachedIn(viewModelScope)
    }

    suspend fun favoriteMovies(): Flow<PagingData<Movie>> {
        return movieRepository.getFavoriteMovies(4)
    }

    fun getLatestMovie() {
        viewModelScope.launch {
            movieRepository.getLatestMovie(Locale.getDefault().displayLanguage).collectLatest { movieResult ->
                if (movieResult is Result.Success) {
                    _latestMovie.postValue(movieResult.data!!)
                }
            }
        }
    }

    fun updateFavorite(movie: Movie) {
        viewModelScope.launch {
            movieRepository.updateFavorite(movie)
        }
    }
}
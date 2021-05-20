package com.lukmannudin.moviecatalogue.ui.moviesdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepository
import com.lukmannudin.moviecatalogue.utils.Constant.DEFAULT_LANGUAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Lukmannudin on 5/4/21.
 */

@HiltViewModel
class MoviesDetailViewModel @Inject constructor(
    private val moviesRepository: MovieRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val moviesState = MutableLiveData<MovieDetailState>()

    fun getMovie(movieId: Int?) {
        moviesState.value = MovieDetailState.Loading

        if (movieId == null) return

        viewModelScope.launch(ioDispatcher) {
            when (val movie = moviesRepository.getMovie(movieId, DEFAULT_LANGUAGE)){
                is Result.Error -> {
                    moviesState.postValue(MovieDetailState.Error(movie.exception.message.toString()))
                }
                is Result.Success -> {
                    moviesState.postValue(MovieDetailState.Loaded(movie.data))
                }
            }
        }
    }

    sealed class MovieDetailState {
        object Loading: MovieDetailState()
        data class Error(val errorMessage: String): MovieDetailState()
        data class Loaded(val movie: Movie) : MovieDetailState()
    }
}
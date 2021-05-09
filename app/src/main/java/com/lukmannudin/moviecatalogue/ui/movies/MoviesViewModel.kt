package com.lukmannudin.moviecatalogue.ui.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Lukmannudin on 5/3/21.
 */

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepositoryImpl
) : ViewModel() {

    val moviesState = MutableLiveData<MoviesState>()

    private val language = "en-US"
    private val page = 1

    fun getMovies() {
        moviesState.value = MoviesState.Loading

        viewModelScope.launch {
           when (val movies = movieRepository.getPopularMovies(language, page)){
                is Result.Error -> {
                    moviesState.postValue(MoviesState.Error(movies.exception.message.toString()))
                }

                is Result.Success -> {
                    moviesState.postValue(MoviesState.Loaded(movies.data))
                }

                else -> {
                    moviesState.postValue(MoviesState.Error("something wrong"))
                }
            }
        }
    }

    sealed class MoviesState(){
        object Loading : MoviesState()
        data class Error(val errorMessage: String) : MoviesState()
        data class Loaded(val movies: List<Movie>) : MoviesState()
    }

}
package com.lukmannudin.moviecatalogue.ui.moviesdetail

import androidx.lifecycle.ViewModel
import com.lukmannudin.moviecatalogue.data.Movie

/**
 * Created by Lukmannudin on 5/4/21.
 */


class MoviesDetailViewModel : ViewModel() {
    private var currentMovie: MovieState? = null

    fun setMovie(movie: Movie?) {
        currentMovie = if (movie != null) {
            MovieState.Loaded(movie)
        } else {
            MovieState.Failure
        }
    }

    fun getMovie(): MovieState? {
        return currentMovie
    }

    sealed class MovieState {
        data class Loaded(val movie: Movie) : MovieState()
        object Failure : MovieState()
    }
}
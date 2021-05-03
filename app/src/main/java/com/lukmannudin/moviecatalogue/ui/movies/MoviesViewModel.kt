package com.lukmannudin.moviecatalogue.ui.movies

import androidx.lifecycle.ViewModel
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.MoviesDummy

/**
 * Created by Lukmannudin on 5/3/21.
 */

class MoviesViewModel : ViewModel() {
    fun getMovies(): List<Movie> = MoviesDummy.generateDummyMovies()
}
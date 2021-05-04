package com.lukmannudin.moviecatalogue.ui.moviesdetail

import com.google.common.base.Verify.verify
import com.lukmannudin.moviecatalogue.data.MoviesDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * Created by Lukmannudin on 5/4/21.
 */

class MoviesDetailViewModelTest {

    private lateinit var viewModel: MoviesDetailViewModel
    private val dummyMovie = MoviesDummy.generateDummyMovies()[0]

    @Before
    fun setup(){
        viewModel = MoviesDetailViewModel()
    }

    @Test
    fun getMovieLoaded() {
        viewModel.setMovie(dummyMovie)

        val movieState = viewModel.getMovie()
        assertNotNull(movieState)
        verify(movieState is MoviesDetailViewModel.MovieState.Loaded)

        movieState as MoviesDetailViewModel.MovieState.Loaded
        movieState.movie.let { movie ->
            assertEquals(dummyMovie.id, movie.id)
            assertEquals(dummyMovie.title, movie.title)
            assertEquals(dummyMovie.overview, movie.overview)
            assertEquals(dummyMovie.posterPath, movie.posterPath)
            assertEquals(dummyMovie.releaseDate, movie.releaseDate)
            assertEquals(dummyMovie.userScore, movie.userScore)
        }
    }

    @Test
    fun getMovieFailure() {
        viewModel.setMovie(null)

        val movieState = viewModel.getMovie()
        assertNotNull(movieState)
        verify(movieState is MoviesDetailViewModel.MovieState.Failure)
    }

}
package com.lukmannudin.moviecatalogue.ui.movies

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * Created by Lukmannudin on 5/4/21.
 */


class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setup(){
        viewModel = MoviesViewModel()
    }

    @Test
    fun getMovies() {
        val movies = viewModel.getMovies()
        assertNotNull(movies)
        assertEquals(10, movies.size)
    }
}
package com.lukmannudin.moviecatalogue.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepositoryImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

/**
 * Created by Lukmannudin on 5/4/21.
 */


class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MovieRepositoryImpl

    @Mock
    private lateinit var observer: Observer<List<Movie>>

    @Before
    fun setup(){
        viewModel = MoviesViewModel(moviesRepository)
    }

    @Test
    fun getMoviesSuccess() {
//        val movies = viewModel.getMovies()
//        assertNotNull(movies)
//
//        `when`(moviesRepository.getPopularMovies(Constant.LANGUAGE, Constant.PAGE))
//            .thenReturn(Result.Success(emptyList()))
//        assertEquals(10, movies.size)
    }

    private val movieDummy =  Movie(
        615457,
        "Nobody",
        "Hutch Mansell, a suburban dad, overlooked husband, nothing neighbor — a \"nobody.\" When two thieves break into his home one night, Hutch's unknown long-simmering rage is ignited and propels him on a brutal path that will uncover dark secrets he fought to leave behind.",
        "Mar 26, 2021",
        0.85f,
        "https://www.themoviedb.org/t/p/w440_and_h660_face/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg"
    )
}
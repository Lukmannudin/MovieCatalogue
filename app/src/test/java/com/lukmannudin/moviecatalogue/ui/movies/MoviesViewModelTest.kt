package com.lukmannudin.moviecatalogue.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepository
import com.lukmannudin.moviecatalogue.utils.Constant
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Lukmannudin on 5/4/21.
 */


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MovieRepository

    @Before
    fun setup(){
        viewModel = MoviesViewModel(moviesRepository, testDispatcher)
    }

    @Test
    fun getMoviesSuccess() = runBlockingTest {
        `when`(moviesRepository.getPopularMovies(Constant.LANGUAGE, Constant.PAGE))
            .thenReturn(Result.Success(emptyList()))

        viewModel.getMovies()

        verify(moviesRepository).getPopularMovies(Constant.LANGUAGE, Constant.PAGE)

        val movies = viewModel.moviesState.value

        assertEquals(MoviesViewModel.MoviesState.Loaded(emptyList()), movies)
    }

    @Test
    fun getMoviesFailed() = runBlockingTest {
        `when`(moviesRepository.getPopularMovies(Constant.LANGUAGE, Constant.PAGE))
            .thenReturn(Result.Error(Exception("")))

        viewModel.getMovies()

        verify(moviesRepository).getPopularMovies(Constant.LANGUAGE, Constant.PAGE)

        val movies = viewModel.moviesState.value

        assertEquals(MoviesViewModel.MoviesState.Error(""), movies)
    }
}
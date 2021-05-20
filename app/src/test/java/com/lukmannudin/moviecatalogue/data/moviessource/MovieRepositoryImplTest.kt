package com.lukmannudin.moviecatalogue.data.moviessource

import com.lukmannudin.moviecatalogue.MainCoroutineRule
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.utils.Constant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Lukmannudin on 10/05/21.
 */


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var remoteMovieDataSource: FakeMovieDataSource

    private lateinit var localMovieDataSource: FakeMovieDataSource

    @Mock
    private lateinit var movieRepository: MovieRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        remoteMovieDataSource = FakeMovieDataSource()
        localMovieDataSource = FakeMovieDataSource()
        movieRepository = MovieRepositoryImpl(
            remoteMovieDataSource, localMovieDataSource, testDispatcher
        )
    }

    @Test
    fun getValidPopularMovies() = runBlockingTest {
        val movies = movieRepository.getPopularMovies(Constant.DEFAULT_LANGUAGE, Constant.DEFAULT_PAGE_INDEX)
        assertEquals(Result.Success(emptyList<Movie>()), movies)
    }

    @Test
    fun getValidMovie() = runBlockingTest {
        val movie = movieRepository.getMovie(1, Constant.DEFAULT_LANGUAGE)
        assertEquals(Result.Success(FakeMovieDataSource.dummyMovie), movie)
    }

    @Test
    fun getInvalidPopularMovies() = runBlockingTest {
        val moviesInvalidLanguage = movieRepository.getPopularMovies("", Constant.DEFAULT_PAGE_INDEX)
        assertNotEquals(Result.Success(emptyList<Movie>()), moviesInvalidLanguage)

        val moviesInvalidPage = movieRepository.getPopularMovies(Constant.DEFAULT_LANGUAGE, -1)
        assertNotEquals(Result.Success(emptyList<Movie>()), moviesInvalidPage)

        val moviesInvalid = movieRepository.getPopularMovies("", -1)
        assertNotEquals(Result.Success(emptyList<Movie>()), moviesInvalid)
    }

    @Test
    fun getInvalidMovie() = runBlockingTest {
        val fakeMovie = FakeMovieDataSource.dummyMovie

        val movieInvalidId = movieRepository.getMovie(-1, Constant.DEFAULT_LANGUAGE)
        assertNotEquals(Result.Success(fakeMovie), movieInvalidId)

        val movieInvalidLanguage = movieRepository.getMovie(1, "")
        assertNotEquals(Result.Success(fakeMovie), movieInvalidLanguage)

        val movieInvalid = movieRepository.getMovie(-1, "")
        assertNotEquals(Result.Success(fakeMovie), movieInvalid)
    }
}
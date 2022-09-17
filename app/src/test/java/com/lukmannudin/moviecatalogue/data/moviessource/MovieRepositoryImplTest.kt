package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.ExperimentalPagingApi
import com.lukmannudin.moviecatalogue.DummiesTest
import com.lukmannudin.moviecatalogue.MainCoroutineRule
import com.lukmannudin.moviecatalogue.data.PagingCatalogueConfig
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
@ExperimentalPagingApi
@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var remoteMovieDataSource: MovieDataSource

    @Mock
    private lateinit var localRemoteDataSource: MovieDataSource

    @ExperimentalPagingApi
    @Mock
    private lateinit var pagingDataSource: PagingDataSource<Movie>

    @Mock
    private lateinit var movieRepository: MovieRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() = runTest {

        remoteMovieDataSource = FakeMovieDataSource()
        localRemoteDataSource = FakeMovieDataSource()
        pagingDataSource = FakeMoviePagingDataSource()

        movieRepository = MovieRepositoryImpl(
            remoteMovieDataSource,
            localRemoteDataSource,
            pagingDataSource,
            testDispatcher
        )
    }

    @Test
    fun getMovie() = runTest {
        val successMovie = movieRepository.getMovie(
            DummiesTest.dummyMovie.id,
            PagingCatalogueConfig.DEFAULT_LANGUAGE
        ).last()
        assertEquals(Result.Success(DummiesTest.dummyMovie), successMovie)

        val errorMovie = movieRepository.getMovie(-1, PagingCatalogueConfig.DEFAULT_LANGUAGE).last()
        assertTrue(errorMovie is Result.Error)
    }
}
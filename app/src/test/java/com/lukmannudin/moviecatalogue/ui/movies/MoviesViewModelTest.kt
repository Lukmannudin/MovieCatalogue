package com.lukmannudin.moviecatalogue.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.*
import com.lukmannudin.moviecatalogue.DummiesTest
import com.lukmannudin.moviecatalogue.MainCoroutineRule
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepository
import com.lukmannudin.moviecatalogue.ui.movies.moviesdetail.MoviesCallback
import com.lukmannudin.moviecatalogue.ui.movies.moviesdetail.NoopListCallback
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import org.junit.After
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

    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    @Mock
    private lateinit var moviesRepository: MovieRepository

    @Before
    fun setup() {
        viewModel = MoviesViewModel(moviesRepository, testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getMovies() = testScope.runTest {
        val data = PagingData.from(listOf(DummiesTest.dummyMovie))
        val differ = AsyncPagingDataDiffer(
            diffCallback = MoviesCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(data)
        advanceUntilIdle()
        assertEquals(listOf(DummiesTest.dummyMovie), differ.snapshot().items)
    }
}
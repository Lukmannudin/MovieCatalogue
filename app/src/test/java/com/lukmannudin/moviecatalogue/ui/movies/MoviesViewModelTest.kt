package com.lukmannudin.moviecatalogue.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.*
import com.lukmannudin.moviecatalogue.DummiesTest
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepository
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
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
    fun setup() {
        viewModel = MoviesViewModel(moviesRepository, testDispatcher)
    }

    @Test
    fun getMovies() = runBlockingTest {
        `when`(moviesRepository.getPopularMovies())
            .thenReturn(flow { emit(PagingData.from(listOf(DummiesTest.dummyMovie))) })

        moviesRepository.getPopularMovies()

        verify(moviesRepository).getPopularMovies()

        val movies = viewModel.movies()

        assertEquals(
            listOf(DummiesTest.dummyMovie),
            movies.take(1).toList().first().collectDataForTest()
        )
    }

    @Test
    fun getFavoriteMovies() = runBlockingTest {
        `when`(moviesRepository.getFavoriteMovies(4))
            .thenReturn(flow { emit(PagingData.from(listOf(DummiesTest.dummyMovie))) })

        moviesRepository.getFavoriteMovies(4)

        verify(moviesRepository).getFavoriteMovies(4)

        val favorites = viewModel.favoriteMovies()

        assertEquals(
            listOf(DummiesTest.dummyMovie),
            favorites.take(1).toList().first().collectDataForTest()
        )
    }

    @ExperimentalCoroutinesApi
    private suspend fun <T : Any> PagingData<T>.collectDataForTest(): List<T> {
        val dcb = object : DifferCallback {
            override fun onChanged(position: Int, count: Int) {}
            override fun onInserted(position: Int, count: Int) {}
            override fun onRemoved(position: Int, count: Int) {}
        }
        val items = mutableListOf<T>()
        val dif = object : PagingDataDiffer<T>(dcb, TestCoroutineDispatcher()) {
            override suspend fun presentNewList(
                previousList: NullPaddedList<T>,
                newList: NullPaddedList<T>,
                newCombinedLoadStates: CombinedLoadStates,
                lastAccessedIndex: Int,
                onListPresentable: () -> Unit
            ): Int? {
                for (idx in 0 until newList.size)
                    items.add(newList.getFromStorage(idx))
                onListPresentable()
                return null
            }
        }
        dif.collectFrom(this)
        return items
    }
}
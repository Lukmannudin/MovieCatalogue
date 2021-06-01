package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.*
import com.lukmannudin.moviecatalogue.DummiesTest
import com.lukmannudin.moviecatalogue.MainCoroutineRule
import com.lukmannudin.moviecatalogue.data.PagingCatalogueConfig
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
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
    fun createRepository() = runBlockingTest {

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
    fun getPopularMovies() = runBlockingTest {
        val movies = movieRepository.getPopularMovies()
        val firstItem = movies.take(1).toList().first().collectDataForTest()
        assertEquals(listOf(DummiesTest.dummyMovie), firstItem)
    }

    @Test
    fun getFavoriteMovie() = runBlockingTest {
        val movies = movieRepository.getFavoriteMovies(1)
        val firstItem = movies.take(1).toList().first().collectDataForTest()
        assertEquals(listOf(DummiesTest.dummyMovie), firstItem)
    }

    @Test
    fun getMovie() = runBlockingTest {
        val successMovie = movieRepository.getMovie(
            DummiesTest.dummyMovie.id,
            PagingCatalogueConfig.DEFAULT_LANGUAGE
        ).last()
        assertEquals(Result.Success(DummiesTest.dummyMovie), successMovie)

        val errorMovie = movieRepository.getMovie(-1, PagingCatalogueConfig.DEFAULT_LANGUAGE).last()
        assertTrue(errorMovie is Result.Error)
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
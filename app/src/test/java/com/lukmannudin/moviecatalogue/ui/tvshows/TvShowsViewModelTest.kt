import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.*
import com.lukmannudin.moviecatalogue.DummiesTest
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepository
import com.lukmannudin.moviecatalogue.ui.tvshows.TvShowsViewModel
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
class TvShowsViewModelTest {

    private lateinit var viewModel: TvShowsViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvShowRepository: TvShowRepository

    @Before
    fun setup() {
        viewModel = TvShowsViewModel(tvShowRepository, testDispatcher)
    }

    @Test
    fun getTvShows() = runBlockingTest {
        `when`(tvShowRepository.getPopularTvShows())
            .thenReturn(flow { emit(PagingData.from(listOf(DummiesTest.dummyTvShow))) })

        tvShowRepository.getPopularTvShows()

        verify(tvShowRepository).getPopularTvShows()

        val tvShows = viewModel.tvShows()

        assertEquals(
            listOf(DummiesTest.dummyTvShow),
            tvShows.take(1).toList().first().collectDataForTest()
        )
    }

    @Test
    fun getFavoriteMovies() = runBlockingTest {
        `when`(tvShowRepository.getFavoriteTvShows(4))
            .thenReturn(flow { emit(PagingData.from(listOf(DummiesTest.dummyTvShow))) })

        tvShowRepository.getFavoriteTvShows(4)

        verify(tvShowRepository).getFavoriteTvShows(4)

        val favorites = viewModel.favoriteTvShows()

        assertEquals(
            listOf(DummiesTest.dummyTvShow),
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
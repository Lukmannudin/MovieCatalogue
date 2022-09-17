import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.*
import com.lukmannudin.moviecatalogue.DummiesTest
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepository
import com.lukmannudin.moviecatalogue.ui.movies.moviesdetail.MoviesCallback
import com.lukmannudin.moviecatalogue.ui.movies.moviesdetail.NoopListCallback
import com.lukmannudin.moviecatalogue.ui.tvshows.TvShowCallback
import com.lukmannudin.moviecatalogue.ui.tvshows.TvShowsViewModel
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
class TvShowsViewModelTest {

    private lateinit var viewModel: TvShowsViewModel
    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    @Mock
    private lateinit var tvShowRepository: TvShowRepository

    @Before
    fun setup() {
        viewModel = TvShowsViewModel(tvShowRepository, testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getTvShows() = testScope.runTest {
        val data = PagingData.from(listOf(DummiesTest.dummyTvShow))
        val differ = AsyncPagingDataDiffer(
            diffCallback = TvShowCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(data)
        advanceUntilIdle()
        assertEquals(listOf(DummiesTest.dummyTvShow), differ.snapshot().items)
    }
}
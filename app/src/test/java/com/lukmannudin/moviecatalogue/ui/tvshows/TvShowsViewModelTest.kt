
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepository
import com.lukmannudin.moviecatalogue.ui.tvshows.TvShowsViewModel
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
class TvShowsViewModelTest {

    private lateinit var viewModel: TvShowsViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvShowRepository: TvShowRepository

    @Before
    fun setup(){
        viewModel = TvShowsViewModel(tvShowRepository, testDispatcher)
    }

    @Test
    fun getTvShowsSuccess() = runBlockingTest {
        `when`(tvShowRepository.getPopularTvShows(Constant.DEFAULT_LANGUAGE, Constant.DEFAULT_PAGE_INDEX))
            .thenReturn(Result.Success(emptyList()))

        viewModel.getTvShows()

        verify(tvShowRepository).getPopularTvShows(Constant.DEFAULT_LANGUAGE, Constant.DEFAULT_PAGE_INDEX)

        val tvShows = viewModel.tvShowsState.value

        assertEquals(TvShowsViewModel.TvShowsState.Loaded(emptyList()), tvShows)
    }

    @Test
    fun getTvShowsFailed() = runBlockingTest {
        `when`(tvShowRepository.getPopularTvShows(Constant.DEFAULT_LANGUAGE, Constant.DEFAULT_PAGE_INDEX))
            .thenReturn(Result.Error(Exception("")))

        viewModel.getTvShows()

        verify(tvShowRepository).getPopularTvShows(Constant.DEFAULT_LANGUAGE, Constant.DEFAULT_PAGE_INDEX)

        val tvShows = viewModel.tvShowsState.value

        assertEquals(TvShowsViewModel.TvShowsState.Error(""), tvShows)
    }
}
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukmannudin.moviecatalogue.DummiesTest
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepository
import com.lukmannudin.moviecatalogue.ui.tvshows.tvshowsdetail.TvShowsDetailViewModel
import com.lukmannudin.moviecatalogue.utils.PagingCatalogueConfig
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
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
class TvShowsDetailViewModelTest {

    private lateinit var viewModel: TvShowsDetailViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvShowRepository: TvShowRepository

    @Before
    fun setup() {
        viewModel = TvShowsDetailViewModel(tvShowRepository, testDispatcher)
    }

    @Test
    fun getTvShowDetailSuccess() = runBlockingTest {
        val fakeTvShow = DummiesTest.dummyTvShow

        `when`(tvShowRepository.getTvShow(1, PagingCatalogueConfig.DEFAULT_LANGUAGE))
            .thenReturn(flow { emit(Result.Success(fakeTvShow)) })

        viewModel.getTvShow(1)

        verify(tvShowRepository).getTvShow(1, PagingCatalogueConfig.DEFAULT_LANGUAGE)

        val tvShow = viewModel.tvShowState.value

        Assert.assertEquals(TvShowsDetailViewModel.TvShowDetailState.Loaded(fakeTvShow), tvShow)
    }

    @Test
    fun getTvShowDetailFailed() = runBlockingTest {
        `when`(tvShowRepository.getTvShow(1, PagingCatalogueConfig.DEFAULT_LANGUAGE))
            .thenReturn(flow { emit(Result.Error(Exception(""))) })

        viewModel.getTvShow(1)

        verify(tvShowRepository).getTvShow(1, PagingCatalogueConfig.DEFAULT_LANGUAGE)

        val tvShow = viewModel.tvShowState.value

        Assert.assertEquals(TvShowsDetailViewModel.TvShowDetailState.Error(""), tvShow)
    }
}
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukmannudin.moviecatalogue.DummiesTest
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepository
import com.lukmannudin.moviecatalogue.ui.movies.moviesdetail.MoviesDetailViewModel
import com.lukmannudin.moviecatalogue.utils.PagingCatalogueConfig
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
class MoviesDetailViewModelTest {

    private lateinit var viewModel: MoviesDetailViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MovieRepository

    @Before
    fun setup() {
        viewModel = MoviesDetailViewModel(moviesRepository, testDispatcher)
    }

    @Test
    fun getMovieDetailSuccess() = runBlockingTest {
        val fakeMovie = DummiesTest.dummyMovie

        `when`(moviesRepository.getMovie(1, PagingCatalogueConfig.DEFAULT_LANGUAGE))
            .thenReturn(flow { emit(Result.Success(fakeMovie)) })

        viewModel.getMovie(1)

        verify(moviesRepository).getMovie(1, PagingCatalogueConfig.DEFAULT_LANGUAGE)

        val movie = viewModel.moviesState.value

        assertEquals(MoviesDetailViewModel.MovieDetailState.Loaded(fakeMovie), movie)
    }

    @Test
    fun getMovieDetailFailed() = runBlockingTest {
        `when`(moviesRepository.getMovie(1, PagingCatalogueConfig.DEFAULT_LANGUAGE))
            .thenReturn( flow { emit(Result.Error(Exception(""))) })

        viewModel.getMovie(1)

        verify(moviesRepository).getMovie(1, PagingCatalogueConfig.DEFAULT_LANGUAGE)

        val movie = viewModel.moviesState.value

        assertEquals(MoviesDetailViewModel.MovieDetailState.Error(""), movie)
    }
}
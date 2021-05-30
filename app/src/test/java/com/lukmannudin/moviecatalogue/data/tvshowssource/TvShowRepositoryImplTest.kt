import androidx.paging.*
import com.lukmannudin.moviecatalogue.DummiesTest
import com.lukmannudin.moviecatalogue.MainCoroutineRule
import com.lukmannudin.moviecatalogue.data.PagingCatalogueConfig
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.FakeTvShowPagingDataSource
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowDataSource
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepository
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Lukmannudin on 11/05/21.
 */


@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(MockitoJUnitRunner::class)
class TvShowRepositoryImplTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var localTvShowDataSource: TvShowDataSource

    @Mock
    private lateinit var remoteTvShowDataSource: TvShowDataSource

    @ExperimentalPagingApi
    @Mock
    private lateinit var pagingDataSource: PagingDataSource<TvShow>

    @Mock
    private lateinit var tvShowRepository: TvShowRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() = runBlockingTest {

        localTvShowDataSource = FakeTvShowDataSource()
        remoteTvShowDataSource = FakeTvShowDataSource()
        pagingDataSource = FakeTvShowPagingDataSource()

        tvShowRepository = TvShowRepositoryImpl(
            localTvShowDataSource,
            remoteTvShowDataSource,
            pagingDataSource,
            testDispatcher
        )
    }


    @Test
    fun getPopularTvShows() = runBlockingTest {
        val tvShows = tvShowRepository.getPopularTvShows()
        val firstItem = tvShows.take(1).toList().first().collectDataForTest()
        Assert.assertEquals(listOf(DummiesTest.dummyTvShow), firstItem)
    }

    @Test
    fun getFavoriteTvShow() = runBlockingTest {
        val movies = tvShowRepository.getFavoriteTvShows(1)
        val firstItem = movies.take(1).toList().first().collectDataForTest()
        Assert.assertEquals(listOf(DummiesTest.dummyTvShow), firstItem)
    }

    @Test
    fun getTvShow() = runBlockingTest {
        val successTvShow = tvShowRepository.getTvShow(
            DummiesTest.dummyTvShow.id,
            PagingCatalogueConfig.DEFAULT_LANGUAGE
        ).last()
        Assert.assertEquals(Result.Success(DummiesTest.dummyTvShow), successTvShow)


        val errorTvShow = tvShowRepository.getTvShow(-1, PagingCatalogueConfig.DEFAULT_LANGUAGE).last()
        Assert.assertTrue(errorTvShow is Result.Error)
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
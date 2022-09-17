import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowDataSource
import com.lukmannudin.moviecatalogue.DummiesTest
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import kotlinx.coroutines.flow.Flow
import com.lukmannudin.moviecatalogue.data.entity.Result
import kotlinx.coroutines.flow.flow

/**
 * Created by Lukmannudin on 11/05/21.
 */

class FakeTvShowDataSource : TvShowDataSource {
    override suspend fun getPopularTvShows(language: String, page: Int): Flow<PagingData<TvShow>> {
        return flow {
            emit(PagingData.from(listOf(DummiesTest.dummyTvShow)))
        }
    }

    override suspend fun getFavoriteTvShows(pageSize: Int): Flow<PagingData<TvShow>> {
        return flow {
            emit(PagingData.from(listOf(DummiesTest.dummyTvShow)))
        }
    }

    override suspend fun getTvShow(id: Int, language: String): Result<TvShow> {
        return if (id != DummiesTest.dummyTvShow.id) {
            Result.Error(Exception(DummiesTest.errorMessage))
        } else {
            Result.Success(DummiesTest.dummyTvShow)
        }
    }

    override suspend fun saveTvShows(TvShows: List<TvShow>) {
        // do nothing
    }

    override suspend fun saveTvShow(TvShow: TvShow) {
        // do nothing
    }

    override suspend fun updateFavorite(TvShow: TvShow) {
        // do nothing
    }

    override suspend fun updateTvShow(TvShow: TvShow) {
        // do nothing
    }

    override suspend fun getOnAirTvShows(language: String, page: Int): Flow<PagingData<TvShow>> {
        return flow {
            emit(PagingData.from(listOf(DummiesTest.dummyTvShow)))
        }
    }

    override suspend fun getLatestTvShow(language: String): Result<TvShow> {
        return Result.Success(DummiesTest.dummyTvShow)
    }
}
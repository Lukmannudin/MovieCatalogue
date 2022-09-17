package com.lukmannudin.moviecatalogue.data.tvshowssource

import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.DummiesTest
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTvShowPagingDataSource : PagingDataSource<TvShow> {

    override fun getPopularItems(): Flow<PagingData<TvShow>> {
        return flow {
            emit(PagingData.from(listOf(DummiesTest.dummyTvShow)))
        }
    }

    override fun getNowPlayingItems(): Flow<PagingData<TvShow>> {
        return flow {
            emit(PagingData.from(listOf(DummiesTest.dummyTvShow)))
        }
    }
}
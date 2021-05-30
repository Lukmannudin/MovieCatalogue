package com.lukmannudin.moviecatalogue.data.moviessource

import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.DummiesTest
import com.lukmannudin.moviecatalogue.data.PagingDataSource
import com.lukmannudin.moviecatalogue.data.entity.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMoviePagingDataSource : PagingDataSource<Movie> {

    override fun getPopularItems(): Flow<PagingData<Movie>> {
        return flow {
            emit(PagingData.from(listOf(DummiesTest.dummyMovie)))
        }
    }
}
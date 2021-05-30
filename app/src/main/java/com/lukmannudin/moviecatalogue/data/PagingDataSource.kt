package com.lukmannudin.moviecatalogue.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * Created by Lukmannudin on 26/05/21.
 */
interface PagingDataSource<T : Any> {
    fun getPopularItems(): Flow<PagingData<T>>
}

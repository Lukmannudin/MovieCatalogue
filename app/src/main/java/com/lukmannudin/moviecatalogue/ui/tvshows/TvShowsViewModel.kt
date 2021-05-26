package com.lukmannudin.moviecatalogue.ui.tvshows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepository
import com.lukmannudin.moviecatalogue.utils.PagingCatalogueConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Lukmannudin on 5/3/21.
 */

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val tvShowRepository: TvShowRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    suspend fun tvShows(): Flow<PagingData<TvShow>> {
        return tvShowRepository.getPopularTvShows().cachedIn(viewModelScope)
    }

    fun updateFavorite(tvShow: TvShow){
        viewModelScope.launch {
            tvShowRepository.updateFavorite(tvShow)
        }
    }
}
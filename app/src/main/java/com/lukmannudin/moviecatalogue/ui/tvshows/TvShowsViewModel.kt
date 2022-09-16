package com.lukmannudin.moviecatalogue.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepository
import com.lukmannudin.moviecatalogue.utils.PagingCatalogueConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * Created by Lukmannudin on 5/3/21.
 */

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val tvShowRepository: TvShowRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _latestTvShow = MutableLiveData<TvShow>()
    val latestTvShow: LiveData<TvShow> = _latestTvShow

    suspend fun popularTvShows(): Flow<PagingData<TvShow>> {
        return tvShowRepository.getPopularTvShows().cachedIn(viewModelScope)
    }

    suspend fun onAirTvShows(): Flow<PagingData<TvShow>> {
        return tvShowRepository.getOnAirTvShows().cachedIn(viewModelScope)
    }

    suspend fun favoriteTvShows(): Flow<PagingData<TvShow>>{
        return tvShowRepository.getFavoriteTvShows(4)
    }

    fun getLatestTvShow() {
        viewModelScope.launch {
            tvShowRepository.getLatestTvShow(Locale.getDefault().displayLanguage).collectLatest { movieResult ->
                if (movieResult is Result.Success) {
                    _latestTvShow.postValue(movieResult.data!!)
                }
            }
        }
    }

    fun updateFavorite(tvShow: TvShow){
        viewModelScope.launch {
            tvShowRepository.updateFavorite(tvShow)
        }
    }
}
package com.lukmannudin.moviecatalogue.ui.tvshows.tvshowsdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukmannudin.moviecatalogue.data.entity.Result
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepository
import com.lukmannudin.moviecatalogue.utils.PagingCatalogueConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Lukmannudin on 5/4/21.
 */

@HiltViewModel
class TvShowsDetailViewModel @Inject constructor(
    private val tvShowRepository: TvShowRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    val tvShowState = MutableLiveData<TvShowDetailState>()

    fun getTvShow(tvShowId: Int?) {
        if (tvShowId == null) return

        tvShowState.value = TvShowDetailState.Loading

        viewModelScope.launch(ioDispatcher) {
            tvShowRepository.getTvShow(tvShowId,
                PagingCatalogueConfig.DEFAULT_LANGUAGE
            ).collectLatest { resultMovie ->
                when (resultMovie) {
                    is Result.Error -> {
                        tvShowState.postValue(TvShowDetailState.Error(resultMovie.exception.message.toString()))
                    }
                    is Result.Success -> {
                        tvShowState.postValue(TvShowDetailState.Loaded(resultMovie.data))
                    }
                    else -> {
                        tvShowState.postValue(TvShowDetailState.Loading)
                    }
                }
            }
        }
    }

    sealed class TvShowDetailState {
        object Loading: TvShowDetailState()
        data class Error(val errorMessage: String): TvShowDetailState()
        data class Loaded(val tvShow: TvShow) : TvShowDetailState()
    }
}
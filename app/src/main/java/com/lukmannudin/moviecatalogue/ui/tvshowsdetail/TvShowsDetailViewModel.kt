package com.lukmannudin.moviecatalogue.ui.tvshowsdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepository
import com.lukmannudin.moviecatalogue.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Lukmannudin on 5/4/21.
 */

@HiltViewModel
class TvShowsDetailViewModel @Inject constructor(
    private val tvShowRepository: TvShowRepository
): ViewModel() {

    val tvShowState = MutableLiveData<TvShowDetailState>()

    fun getTvShow(tvShowId: Int?) {
        if (tvShowId == null) return

        tvShowState.value = TvShowDetailState.Loading

        viewModelScope.launch {
            when (val tvShow = tvShowRepository.getTvShow(tvShowId, Constant.LANGUAGE)){
                is Result.Error -> {
                    tvShowState.postValue(TvShowDetailState.Error(
                        tvShow.exception.message.toString()
                    ))
                }
                is Result.Success -> {
                    tvShowState.postValue(TvShowDetailState.Loaded(
                        tvShow.data
                    ))
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
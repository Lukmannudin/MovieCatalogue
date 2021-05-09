package com.lukmannudin.moviecatalogue.ui.tvshows

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.TvShow
import com.lukmannudin.moviecatalogue.data.tvshowssource.TvShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Lukmannudin on 5/3/21.
 */

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val tvShowRepository: TvShowRepository
) : ViewModel() {

    private val language = "en-US"
    private val page = 1

    val tvShowsState = MutableLiveData<TvShowsState>()

    fun getTvShows() {
        tvShowsState.value = TvShowsState.Loading

        viewModelScope.launch {
            when (val tvShows = tvShowRepository.getPopularTvShows(language, page)){
                is Result.Error -> {
                    tvShowsState.postValue(TvShowsState.Error(tvShows.exception.message.toString()))
                }
                is Result.Success -> {
                    tvShowsState.postValue(TvShowsState.Loaded(tvShows.data))
                }
                else -> {
                    tvShowsState.postValue(TvShowsState.Error("something wrong"))
                }
            }
        }
    }

    sealed class TvShowsState {
        object Loading : TvShowsState()
        data class Error(val errorMessage: String) : TvShowsState()
        data class Loaded(val tvShows: List<TvShow>) : TvShowsState()
    }
}
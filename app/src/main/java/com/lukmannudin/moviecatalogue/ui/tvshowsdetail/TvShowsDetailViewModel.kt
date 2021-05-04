package com.lukmannudin.moviecatalogue.ui.tvshowsdetail

import androidx.lifecycle.ViewModel
import com.lukmannudin.moviecatalogue.data.TvShow

/**
 * Created by Lukmannudin on 5/4/21.
 */


class TvShowsDetailViewModel : ViewModel() {
    private var currentTvShow: TvShowState? = null

    fun setTvShow(tvShow: TvShow?) {
        currentTvShow = if (tvShow != null){
            TvShowState.Loaded(tvShow)
        } else {
            TvShowState.Failure
        }
    }

    fun getTvShow(): TvShowState? {
        return currentTvShow
    }

    sealed class TvShowState {
        data class Loaded(val tvShow: TvShow) : TvShowState()
        object Failure: TvShowState()
    }
}
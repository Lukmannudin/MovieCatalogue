package com.lukmannudin.moviecatalogue.ui.tvshows

import androidx.lifecycle.ViewModel
import com.lukmannudin.moviecatalogue.data.TvShow
import com.lukmannudin.moviecatalogue.data.TvShowsDummy

/**
 * Created by Lukmannudin on 5/3/21.
 */

class TvShowsViewModel : ViewModel() {
    fun getTvShows(): List<TvShow> = TvShowsDummy.generateDummyTvShows()
}
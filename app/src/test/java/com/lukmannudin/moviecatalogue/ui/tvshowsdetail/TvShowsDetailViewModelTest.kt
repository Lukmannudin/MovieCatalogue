package com.lukmannudin.moviecatalogue.ui.tvshowsdetail

import com.google.common.base.Verify
import com.lukmannudin.moviecatalogue.data.TvShowsDummy
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Lukmannudin on 5/4/21.
 */


class TvShowsDetailViewModelTest {

    private lateinit var viewModel: TvShowsDetailViewModel
    private val dummyTvShow = TvShowsDummy.generateDummyTvShows()[0]

    @Before
    fun setup() {
        viewModel = TvShowsDetailViewModel()
    }

    @Test
    fun getTvShow() {
        viewModel.setTvShow(dummyTvShow)

        val tvShowState = viewModel.getTvShow()
        Assert.assertNotNull(tvShowState)
        Verify.verify(tvShowState is TvShowsDetailViewModel.TvShowState.Loaded)

        tvShowState as TvShowsDetailViewModel.TvShowState.Loaded
        tvShowState.tvShow.let { tvShow ->
            Assert.assertEquals(dummyTvShow.id, tvShow.id)
            Assert.assertEquals(dummyTvShow.title, tvShow.title)
            Assert.assertEquals(dummyTvShow.overview, tvShow.overview)
            Assert.assertEquals(dummyTvShow.posterPath, tvShow.posterPath)
            Assert.assertEquals(dummyTvShow.releaseDate, tvShow.releaseDate)
            Assert.assertEquals(dummyTvShow.userScore, tvShow.userScore)
        }
    }

    @Test
    fun getTvShowFailure() {
        viewModel.setTvShow(null)

        val movieState = viewModel.getTvShow()
        Assert.assertNotNull(movieState)
        Verify.verify(movieState is TvShowsDetailViewModel.TvShowState.Failure)
    }
}
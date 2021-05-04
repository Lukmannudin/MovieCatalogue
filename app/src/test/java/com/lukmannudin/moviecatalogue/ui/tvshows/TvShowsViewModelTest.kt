package com.lukmannudin.moviecatalogue.ui.tvshows

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * Created by Lukmannudin on 5/4/21.
 */


class TvShowsViewModelTest {

    private lateinit var viewModel: TvShowsViewModel

    @Before
    fun setup(){
        viewModel = TvShowsViewModel()
    }

    @Test
    fun getTvShows() {
        val tvShows = viewModel.getTvShows()
        assertNotNull(tvShows)
        assertEquals(10, tvShows.size)
    }
}
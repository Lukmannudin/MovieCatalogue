package com.lukmannudin.moviecatalogue.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.databinding.ActivityHomeBinding
import com.lukmannudin.moviecatalogue.ui.movies.favoritemovie.FavoriteMovieActivity
import com.lukmannudin.moviecatalogue.ui.tvshows.favoritetvshow.FavoriteTvShowActivity
import com.lukmannudin.moviecatalogue.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Lukmannudin on 5/3/21.
 */

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)

        activityHomeBinding.viewPager.adapter = sectionsPagerAdapter
        activityHomeBinding.tabs.setupWithViewPager(activityHomeBinding.viewPager)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.app_name)
    }
}
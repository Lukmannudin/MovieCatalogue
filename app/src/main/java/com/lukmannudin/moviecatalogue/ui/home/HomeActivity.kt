package com.lukmannudin.moviecatalogue.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.databinding.ActivityHomeBinding
import com.lukmannudin.moviecatalogue.ui.movies.favoritemovie.FavoriteMoviesActivity
import com.lukmannudin.moviecatalogue.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Lukmannudin on 5/3/21.
 */

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EspressoIdlingResource.increment()
        val activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)

        activityHomeBinding.viewPager.adapter = sectionsPagerAdapter
        activityHomeBinding.tabs.setupWithViewPager(activityHomeBinding.viewPager)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun navigateToMoviesFavorite(){
        startActivity(Intent(this, FavoriteMoviesActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_movie_favorite -> {
                navigateToMoviesFavorite()
            }

            R.id.menu_tvshows_favorites -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
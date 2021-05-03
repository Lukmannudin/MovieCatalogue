package com.lukmannudin.moviecatalogue.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lukmannudin.moviecatalogue.databinding.ActivityHomeBinding

/**
 * Created by Lukmannudin on 5/3/21.
 */

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)

        activityHomeBinding.viewPager.adapter = sectionsPagerAdapter
        activityHomeBinding.tabs.setupWithViewPager(activityHomeBinding.viewPager)

        supportActionBar?.elevation = 0f
    }
}
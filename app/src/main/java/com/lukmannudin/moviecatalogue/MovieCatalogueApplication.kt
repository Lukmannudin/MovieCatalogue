package com.lukmannudin.moviecatalogue

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Lukmannudin on 09/05/21.
 */


@HiltAndroidApp
class MovieCatalogueApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // set to cannot force dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
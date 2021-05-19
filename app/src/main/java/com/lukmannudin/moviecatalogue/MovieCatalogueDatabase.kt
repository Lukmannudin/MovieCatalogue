package com.lukmannudin.moviecatalogue

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieDao
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocal

/**
 * Created by Lukmannudin on 19/05/21.
 */

@Database(
    entities = [MovieLocal::class],
    version = 1,
    exportSchema = false
)
abstract class MovieCatalogueDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        fun getInstance(context: Context): MovieCatalogueDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MovieCatalogueDatabase::class.java,
                "movieCatalogue.db"
            ).build()
        }
    }
}
package com.lukmannudin.moviecatalogue

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieDao
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieLocal
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieRemoteKey
import com.lukmannudin.moviecatalogue.data.moviessource.local.MovieRemoteKeyDao

/**
 * Created by Lukmannudin on 19/05/21.
 */

@Database(
    entities = [MovieLocal::class, MovieRemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class MovieCatalogueDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun movieRemoteKeyDao(): MovieRemoteKeyDao
}
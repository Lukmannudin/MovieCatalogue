package com.lukmannudin.moviecatalogue.data.moviessource.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Lukmannudin on 19/05/21.
 */

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getMovies(): DataSource.Factory<Int, MovieLocal>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): MovieLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieLocal>)
}
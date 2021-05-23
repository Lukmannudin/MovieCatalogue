package com.lukmannudin.moviecatalogue.data.moviessource.local

import androidx.paging.PagingSource
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
    fun getMovies(): PagingSource<Int, MovieLocal>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): MovieLocal

    @Query("DELETE FROM movies WHERE id = :movieId")
    fun deleteMovieById(movieId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieLocal>)

    @Query("DELETE FROM movies")
    suspend fun clearCache()
}
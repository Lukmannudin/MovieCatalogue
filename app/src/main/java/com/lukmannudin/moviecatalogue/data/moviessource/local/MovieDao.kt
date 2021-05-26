package com.lukmannudin.moviecatalogue.data.moviessource.local

import androidx.paging.PagingSource
import androidx.room.*

/**
 * Created by Lukmannudin on 19/05/21.
 */

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY releaseDate DESC")
    fun getMovies(): PagingSource<Int, MovieLocal>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): MovieLocal

    @Query("DELETE FROM movies WHERE id = :movieId")
    fun deleteMovieById(movieId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieLocal>)

    @Query("DELETE FROM movies")
    suspend fun clearCache()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieLocal)

    @Query("UPDATE movies SET is_favorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavorite(movieId: Int, isFavorite: Boolean)
}
package com.lukmannudin.moviecatalogue.data.moviessource.local

import androidx.paging.PagingSource
import androidx.room.*

/**
 * Created by Lukmannudin on 19/05/21.
 */

@Dao
interface MovieDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieLocal)

    @Query("SELECT * FROM movies ORDER BY movies.popularity DESC")
    fun getPopularMovies(): PagingSource<Int, MovieLocal>

    @Query("SELECT * FROM movies ORDER BY movies.release_date DESC")
    fun getLatestMovies(): PagingSource<Int, MovieLocal>

    @Query("SELECT * FROM movies WHERE is_favorite = 1")
    fun getFavoriteMovies(): PagingSource<Int, MovieLocal>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): MovieLocal

    @Query("UPDATE movies SET is_favorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavorite(movieId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM movies ORDER BY movies.release_date DESC LIMIT 1")
    suspend fun getLatestMovie(): MovieLocal?

    @Update
    suspend fun updateMovie(movieLocal: MovieLocal)

    @Query("DELETE FROM movies WHERE id = :movieId")
    fun deleteMovieById(movieId: Int)

    @Query("DELETE FROM movies")
    suspend fun clearCache()
}
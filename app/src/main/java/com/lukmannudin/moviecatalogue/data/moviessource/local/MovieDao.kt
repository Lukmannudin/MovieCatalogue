package com.lukmannudin.moviecatalogue.data.moviessource.local

import androidx.paging.PagingSource
import androidx.room.*
import com.lukmannudin.moviecatalogue.data.tvshowssource.local.TvShowLocal

/**
 * Created by Lukmannudin on 19/05/21.
 */

@Dao
interface MovieDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieLocal)

    @Query("SELECT * FROM movies ORDER BY release_date DESC")
    fun getMovies(): PagingSource<Int, MovieLocal>

    @Query("SELECT * FROM movies WHERE is_favorite = 1")
    fun getFavoriteMovies(): PagingSource<Int, MovieLocal>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): MovieLocal

    @Query("UPDATE movies SET is_favorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavorite(movieId: Int, isFavorite: Boolean)

    @Update
    suspend fun updateMovie(movieLocal: MovieLocal)

    @Query("DELETE FROM movies WHERE id = :movieId")
    fun deleteMovieById(movieId: Int)

    @Query("DELETE FROM movies")
    suspend fun clearCache()
}
package com.lukmannudin.moviecatalogue.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.databinding.ItemMovieBinding
import com.lukmannudin.moviecatalogue.ui.movies.moviesdetail.MoviesCallback
import com.lukmannudin.moviecatalogue.ui.movies.moviesdetail.MoviesDetailActivity
import com.lukmannudin.moviecatalogue.utils.setImage

/**
 * Created by Lukmannudin on 5/3/21.
 */

class MoviesAdapter: PagingDataAdapter<Movie, MoviesViewHolder>(MoviesCallback()) {

    lateinit var shareCallback: (Movie) -> Unit
    lateinit var favoriteCallback: (Movie) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemsAcademyBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(
        holder: MoviesViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            item?.let { holder.bind(it) }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
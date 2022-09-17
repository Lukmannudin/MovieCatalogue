package com.lukmannudin.moviecatalogue.ui.movies.moviesdetail

import androidx.recyclerview.widget.DiffUtil
import com.lukmannudin.moviecatalogue.data.entity.Movie

class MoviesCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Movie, newItem: Movie): Any {
        return oldItem.id == newItem.id
    }
}
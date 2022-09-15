package com.lukmannudin.moviecatalogue.ui.movies

import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.databinding.ItemMovieBinding
import com.lukmannudin.moviecatalogue.ui.movies.moviesdetail.MoviesDetailActivity
import com.lukmannudin.moviecatalogue.utils.setImage

class MoviesViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        with(binding) {
            ivPoster.setImage(itemView.context, movie.posterPath)
            itemView.setOnClickListener {
                MoviesDetailActivity.start(itemView.context, movie.id)
            }
        }
    }
}
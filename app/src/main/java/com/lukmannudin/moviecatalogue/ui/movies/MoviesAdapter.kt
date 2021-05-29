package com.lukmannudin.moviecatalogue.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.databinding.ItemMovieBinding
import com.lukmannudin.moviecatalogue.ui.movies.moviesdetail.MoviesDetailActivity
import com.lukmannudin.moviecatalogue.utils.Converters.toStringFormat
import com.lukmannudin.moviecatalogue.utils.setImage

/**
 * Created by Lukmannudin on 5/3/21.
 */

class MoviesAdapter: PagingDataAdapter<Movie, MoviesAdapter.MoviesViewHolder>(DIFF_CALLBACK) {

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
            item?.let { holder.bind(it, shareCallback) }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
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
    }

    inner class MoviesViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, shareCallback: (Movie) -> Unit) {
            with(binding) {
                tvItemTitle.text = movie.title
                tvItemDate.text = movie.releaseDate?.toStringFormat()
                tvItemOverview.text = movie.overview
                ivPoster.setImage(itemView.context, movie.posterPath)
                cbFavorite.isChecked = movie.isFavorite

                ivShare.setOnClickListener {
                    shareCallback.invoke(movie)
                }

                itemView.setOnClickListener {
                    MoviesDetailActivity.start(itemView.context, movie.id)
                }

                cbFavorite.setOnClickListener {
                    movie.isFavorite = cbFavorite.isChecked
                    favoriteCallback.invoke(movie)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, shareCallback) }
    }
}
package com.lukmannudin.moviecatalogue.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.databinding.ItemMovieBinding
import com.lukmannudin.moviecatalogue.ui.moviesdetail.MoviesDetailActivity
import com.lukmannudin.moviecatalogue.utils.Converters.toStringFormat
import com.lukmannudin.moviecatalogue.utils.setImage

/**
 * Created by Lukmannudin on 5/3/21.
 */


class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private var movies = ArrayList<Movie>()

    lateinit var shareCallback: (Movie) -> Unit

    fun setMovies(movies: List<Movie>?) {
        if (movies == null) return
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemsAcademyBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, shareCallback)
    }

    override fun getItemCount(): Int = movies.size


    class MoviesViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, shareCallback: (Movie) -> Unit) {
            with(binding) {
                tvItemTitle.text = movie.title
                tvItemDate.text = movie.releaseDate?.toStringFormat()
                tvItemOverview.text = movie.overview
                ivPoster.setImage(itemView.context, movie.posterPath)

                ivShare.setOnClickListener {
                    shareCallback.invoke(movie)
                }

                itemView.setOnClickListener {
                    MoviesDetailActivity.start(itemView.context, movie.id)
                }
            }
        }
    }
}
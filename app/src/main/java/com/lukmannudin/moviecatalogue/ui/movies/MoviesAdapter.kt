package com.lukmannudin.moviecatalogue.ui.movies

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.databinding.ItemMovieBinding
import com.lukmannudin.moviecatalogue.loadImage
import com.lukmannudin.moviecatalogue.ui.movies.moviesdetail.MoviesDetailActivity

/**
 * Created by Lukmannudin on 5/3/21.
 */


class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    private var movies = ArrayList<Movie>()

    fun setMovies(movies: List<Movie>?) {
        if (movies == null) return
        this.movies.clear()
        this.movies.addAll(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemsAcademyBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size


    class MoviesViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                tvItemTitle.text = movie.title
                tvItemDate.text = movie.releaseDate
                imgPoster.loadImage(itemView.context, movie.posterPath)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, MoviesDetailActivity::class.java)
                    itemView.context.startActivity(intent)
                }
//                itemView.setOnClickListener {
//                    val intent = Intent(itemView.context, DetailCourseActivity::class.java)
//                    intent.putExtra(DetailCourseActivity.EXTRA_COURSE, course.courseId)
//                    itemView.context.startActivity(intent)
//                }
//                Glide.with(itemView.context)
//                    .load(course.imagePath)
//                    .apply(
//                        RequestOptions.placeholderOf(R.drawable.ic_loading)
//                            .error(R.drawable.ic_error))
//                    .into(imgPoster)
            }
        }
    }
}
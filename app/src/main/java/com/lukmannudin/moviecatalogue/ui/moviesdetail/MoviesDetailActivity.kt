package com.lukmannudin.moviecatalogue.ui.moviesdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.databinding.ActivityDetailBinding
import com.lukmannudin.moviecatalogue.databinding.ActivityMoviesDetailBinding
import com.lukmannudin.moviecatalogue.utils.setImage
import com.lukmannudin.moviecatalogue.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesDetailViewModel
    private lateinit var binding: ActivityMoviesDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewBinding()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MoviesDetailViewModel::class.java]

        val movieExtras = intent?.getParcelableExtra<Movie>(MOVIE_EXTRA)
        viewModel.setMovie(movieExtras)

        populateMovie()
    }

    private fun populateMovie() {
        val movieState = viewModel.getMovie()

        if (movieState is MoviesDetailViewModel.MovieState.Loaded) {
            with(binding) {
                movieState.movie.let { movie ->
                    ivPoster.setImage(this@MoviesDetailActivity, movie.posterPath)
                    tvTitle.text = movie.title
                    tvDate.text = movie.releaseDate
                    tvOverview.text = movie.overview
                }
            }
        } else {
            showToast(getString(R.string.failed_load_tvshow))
        }
    }

    private fun setContentViewBinding() {
        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        with(activityDetailBinding.vsContentDetail) {
            setOnInflateListener { _, inflated ->
                binding = ActivityMoviesDetailBinding.bind(inflated)
            }
            layoutResource = R.layout.activity_movies_detail
            inflate()
        }

        setContentView(activityDetailBinding.root)
        setSupportActionBar(activityDetailBinding.toolbar)
    }

    companion object {
        private const val MOVIE_EXTRA = "movie_extra"

        fun start(context: Context, movie: Movie) {
            val intent = Intent(context, MoviesDetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movie)
            context.startActivity(intent)
        }
    }
}
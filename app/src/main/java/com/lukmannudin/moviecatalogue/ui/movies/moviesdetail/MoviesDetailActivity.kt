package com.lukmannudin.moviecatalogue.ui.movies.moviesdetail

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.moviessource.local.Genre
import com.lukmannudin.moviecatalogue.databinding.ActivityDetailBinding
import com.lukmannudin.moviecatalogue.databinding.ActivityMoviesDetailBinding
import com.lukmannudin.moviecatalogue.ui.movies.moviesdetail.MoviesDetailViewModel.MovieDetailState
import com.lukmannudin.moviecatalogue.utils.Converters.toPercentage
import com.lukmannudin.moviecatalogue.utils.Converters.toPercentageNumber
import com.lukmannudin.moviecatalogue.utils.Converters.toStringFormat
import com.lukmannudin.moviecatalogue.utils.EspressoIdlingResource
import com.lukmannudin.moviecatalogue.utils.gone
import com.lukmannudin.moviecatalogue.utils.setImage
import com.lukmannudin.moviecatalogue.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesDetailActivity : AppCompatActivity() {

    private val viewModel: MoviesDetailViewModel by viewModels()
    private lateinit var binding: ActivityMoviesDetailBinding

    private lateinit var srLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewBinding()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movieExtras = intent?.getIntExtra(MOVIE_EXTRA, -1)
        viewModel.getMovie(movieExtras)

        srLayout.setOnRefreshListener {
            viewModel.getMovie(movieExtras)
            srLayout.isRefreshing = false
        }

        setupObserver()
    }


    private fun populateMovie(movie: Movie) {
        with(binding) {
            movie.let { movie ->
                supportActionBar?.title = movie.title

                ivPoster.setImage(this@MoviesDetailActivity, movie.posterPath)
                tvReleaseDate.text = movie.releaseDate?.toStringFormat()
                tvOverview.text = movie.overview
            }
        }
    }

    private fun setContentViewBinding() {
        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        srLayout = activityDetailBinding.root

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

    private fun setupObserver() {
        EspressoIdlingResource.increment()
        viewModel.moviesState.observe(this) { viewState ->
            when (viewState) {
                is MovieDetailState.Loading -> {
                    showLoadingAndHideFailureView(true)
                }
                is MovieDetailState.Error -> {
                    binding.lavFailure.visible()
                }
                is MovieDetailState.Loaded -> {
                    showLoadingAndHideFailureView(false)
                    populateMovie(viewState.movie)
                    EspressoIdlingResource.decrement()
                }
            }
        }
    }

    private fun showLoadingAndHideFailureView(status: Boolean) {
        binding.lavFailure.gone()
        with(binding.lavLoading) {
            if (status) {
                visible()
            } else {
                gone()
            }
        }
    }

    companion object {
        private const val MOVIE_EXTRA = "movie_extra"

        fun start(context: Context, movieId: Int) {
            val intent = Intent(context, MoviesDetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movieId)
            context.startActivity(intent)
        }
    }
}
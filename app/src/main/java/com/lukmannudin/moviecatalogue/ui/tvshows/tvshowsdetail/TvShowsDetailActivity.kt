package com.lukmannudin.moviecatalogue.ui.tvshows.tvshowsdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.chip.Chip
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.data.moviessource.local.Genre
import com.lukmannudin.moviecatalogue.databinding.ActivityDetailBinding
import com.lukmannudin.moviecatalogue.databinding.ActivityMoviesDetailBinding
import com.lukmannudin.moviecatalogue.ui.tvshows.tvshowsdetail.TvShowsDetailViewModel.TvShowDetailState
import com.lukmannudin.moviecatalogue.utils.Converters.toPercentage
import com.lukmannudin.moviecatalogue.utils.Converters.toPercentageNumber
import com.lukmannudin.moviecatalogue.utils.Converters.toStringFormat
import com.lukmannudin.moviecatalogue.utils.EspressoIdlingResource
import com.lukmannudin.moviecatalogue.utils.gone
import com.lukmannudin.moviecatalogue.utils.setImage
import com.lukmannudin.moviecatalogue.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowsDetailActivity : AppCompatActivity() {

    private val viewModel: TvShowsDetailViewModel by viewModels()
    private lateinit var binding: ActivityMoviesDetailBinding

    private lateinit var srLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewBinding()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tvShowExtra = intent?.getIntExtra(TVSHOW_EXTRA, -1)
        viewModel.getTvShow(tvShowExtra)

        srLayout.setOnRefreshListener {
            viewModel.getTvShow(tvShowExtra)
            srLayout.isRefreshing = false
        }

        setupObserver()
    }

    private fun populateTvShow(tvShow: TvShow) {
        with(binding) {
            tvShow.let { tvShow ->
                supportActionBar?.title = tvShow.title

                ivPoster.setImage(this@TvShowsDetailActivity, tvShow.posterPath)
                tvTitle.text = tvShow.title
                tvDate.text = tvShow.releaseDate?.toStringFormat()
                tvOverview.text = tvShow.overview
                tvRating.text = tvShow.userScore.toPercentage()


                pbRating.isEnabled = true
                pbRating.progress = tvShow.userScore.toPercentageNumber()

                popularity.text = tvShow.popularity.toString()

                tvShow.genres?.let { genres ->
                    createchippes(genres)
                }
            }
        }
    }

    private fun createchippes(genres: List<Genre>) {
        for (genre in genres) {
            val chip = Chip(this@TvShowsDetailActivity)
            chip.setChipBackgroundColorResource(android.R.color.transparent)
            chip.text = genre.name
            binding.cgGenres.addView(chip)
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
        viewModel.tvShowState.observe(this, { viewState ->
            when (viewState) {
                is TvShowDetailState.Loading -> {
                    showLoadingAndHideFailureView(true)
                }
                is TvShowDetailState.Error -> {
                    binding.lavFailure.visible()
                }
                is TvShowDetailState.Loaded -> {
                    showLoadingAndHideFailureView(false)
                    populateTvShow(viewState.tvShow)
                    EspressoIdlingResource.decrement()
                }
            }
        })
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
        private const val TVSHOW_EXTRA = "tvshow_extra"

        fun start(context: Context, tvShowId: Int) {
            val intent = Intent(context, TvShowsDetailActivity::class.java)
            intent.putExtra(TVSHOW_EXTRA, tvShowId)
            context.startActivity(intent)
        }
    }
}
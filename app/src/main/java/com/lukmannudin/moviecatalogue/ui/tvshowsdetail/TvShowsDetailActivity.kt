package com.lukmannudin.moviecatalogue.ui.tvshowsdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.data.TvShow
import com.lukmannudin.moviecatalogue.databinding.ActivityDetailBinding
import com.lukmannudin.moviecatalogue.databinding.ActivityMoviesDetailBinding
import com.lukmannudin.moviecatalogue.utils.setImage
import com.lukmannudin.moviecatalogue.utils.showToast

class TvShowsDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: TvShowsDetailViewModel
    private lateinit var binding: ActivityMoviesDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewBinding()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModel()

        val tvShowExtra = intent?.getParcelableExtra<TvShow>(TVSHOW_EXTRA)
        viewModel.setTvShow(tvShowExtra)

        populateMovie()
    }

    private fun populateMovie() {
        val tvShowState = viewModel.getTvShow()

        if (tvShowState is TvShowsDetailViewModel.TvShowState.Loaded){
            with(binding){
                tvShowState.tvShow.let { tvShow ->
                    ivPoster.setImage(this@TvShowsDetailActivity, tvShow.posterPath)
                    tvTitle.text = tvShow.title
                    tvDate.text = tvShow.releaseDate
                    tvOverview.text = tvShow.overview
                }
            }
        } else {
            showToast(getString(R.string.failed_load_movie))
        }
    }

    private fun setContentViewBinding(){
        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        with(activityDetailBinding.vsContentDetail){
            setOnInflateListener { _, inflated ->
                binding = ActivityMoviesDetailBinding.bind(inflated)
            }
            layoutResource = R.layout.activity_movies_detail
            inflate()
        }

        setContentView(activityDetailBinding.root)
        setSupportActionBar(activityDetailBinding.toolbar)
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[TvShowsDetailViewModel::class.java]
    }

    companion object {
        private const val TVSHOW_EXTRA = "tvshow_extra"

        fun start(context: Context, tvShow: TvShow){
            val intent = Intent(context, TvShowsDetailActivity::class.java)
            intent.putExtra(TVSHOW_EXTRA, tvShow)
            context.startActivity(intent)
        }
    }
}
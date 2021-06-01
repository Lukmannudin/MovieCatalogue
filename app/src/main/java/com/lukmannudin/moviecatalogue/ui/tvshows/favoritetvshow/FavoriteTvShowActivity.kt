package com.lukmannudin.moviecatalogue.ui.tvshows.favoritetvshow

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.databinding.LayoutFavoriteMoviesBinding
import com.lukmannudin.moviecatalogue.ui.tvshows.TvShowsAdapter
import com.lukmannudin.moviecatalogue.ui.tvshows.TvShowsLoadStateAdapter
import com.lukmannudin.moviecatalogue.ui.tvshows.TvShowsViewModel
import com.lukmannudin.moviecatalogue.utils.EspressoIdlingResource
import com.lukmannudin.moviecatalogue.utils.gone
import com.lukmannudin.moviecatalogue.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FavoriteTvShowActivity : AppCompatActivity() {

    private lateinit var binding: LayoutFavoriteMoviesBinding

    private val viewModel: TvShowsViewModel by viewModels()

    private lateinit var tvShowsAdapter: TvShowsAdapter
    private lateinit var loadStateAdapter: TvShowsLoadStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutFavoriteMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.tv_shows_favorite)

        setupAdapter()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.favoriteTvShows().collectLatest { pagingData ->
                tvShowsAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launchWhenCreated {
            tvShowsAdapter.loadStateFlow.collectLatest {
                idlingResourceCheck(it)
                if (it.refresh is LoadState.NotLoading) {
                    if (tvShowsAdapter.snapshot().items.isEmpty()) {
                        binding.lavEmpty.visible()
                    } else {
                        binding.lavEmpty.gone()
                    }
                }
            }
        }
    }

    // for ui testing
    private fun idlingResourceCheck(loadState: CombinedLoadStates) {
        if (loadState.refresh == LoadState.Loading) {
            EspressoIdlingResource.increment()
        } else {
            EspressoIdlingResource.decrement()
        }
    }

    private fun setupAdapter() {
        tvShowsAdapter = TvShowsAdapter()
        loadStateAdapter = TvShowsLoadStateAdapter(tvShowsAdapter)

        tvShowsAdapter.shareCallback = { movie ->
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(resources.getString(R.string.share_the_film_now))
                .setText(resources.getString(R.string.share_text, movie.title))
                .startChooser()
        }

        tvShowsAdapter.favoriteCallback = { movie ->
            viewModel.updateFavorite(movie)
        }

        with(binding.rvFavoriteMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowsAdapter.withLoadStateHeaderAndFooter(
                header = loadStateAdapter,
                footer = loadStateAdapter
            )
        }
    }
}
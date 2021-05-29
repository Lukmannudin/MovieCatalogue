package com.lukmannudin.moviecatalogue.ui.movies.favoritemovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ShareCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.databinding.LayoutFavoriteMoviesBinding
import com.lukmannudin.moviecatalogue.ui.movies.MoviesAdapter
import com.lukmannudin.moviecatalogue.ui.movies.MoviesViewModel
import com.lukmannudin.moviecatalogue.ui.movies.MoviesLoadStateAdapter
import com.lukmannudin.moviecatalogue.utils.gone
import com.lukmannudin.moviecatalogue.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FavoriteMovieActivity : AppCompatActivity() {

    private lateinit var binding: LayoutFavoriteMoviesBinding

    private val viewModel: MoviesViewModel by viewModels()

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var loadStateAdapter: MoviesLoadStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutFavoriteMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorite_movies)

        setupAdapter()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.favoriteMovies().collectLatest { pagingData ->
                moviesAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launchWhenCreated {
            moviesAdapter.loadStateFlow.collectLatest {
                if (it.refresh is LoadState.NotLoading){
                    if (moviesAdapter.snapshot().items.isEmpty()){
                        binding.lavEmpty.visible()
                    } else {
                        binding.lavEmpty.gone()
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        moviesAdapter = MoviesAdapter()
        loadStateAdapter = MoviesLoadStateAdapter(moviesAdapter)

        moviesAdapter.shareCallback = { movie ->
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(resources.getString(R.string.share_the_film_now))
                .setText(resources.getString(R.string.share_text, movie.title))
                .startChooser()
        }

        moviesAdapter.favoriteCallback = { movie ->
            viewModel.updateFavorite(movie)
        }

        with(binding.rvFavoriteMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter.withLoadStateHeaderAndFooter(
                header = loadStateAdapter,
                footer = loadStateAdapter
            )
        }
    }
}
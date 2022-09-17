package com.lukmannudin.moviecatalogue.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.databinding.FragmentMovieBinding
import com.lukmannudin.moviecatalogue.utils.EspressoIdlingResource
import com.lukmannudin.moviecatalogue.utils.gone
import com.lukmannudin.moviecatalogue.utils.setImage
import com.lukmannudin.moviecatalogue.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Lukmannudin on 5/3/21.
 */

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularLoadStateAdapter: MoviesLoadStateAdapter
    private lateinit var nowPlayingMoviesAdapter: MoviesAdapter
    private lateinit var nowPlayingLoadStateAdapter: MoviesLoadStateAdapter

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObserver()

        viewModel.getLatestMovie()
    }

    private fun setupAdapter() {
        setPopularMoviesAdapter()
        setNowPlayingMoviesAdapter()
    }

    private fun setPopularMoviesAdapter() {
        popularMoviesAdapter = MoviesAdapter()
        popularLoadStateAdapter = MoviesLoadStateAdapter(popularMoviesAdapter)

        popularMoviesAdapter.shareCallback = { movie ->
            if (activity != null) {
                val mimeType = "text/plain"
                ShareCompat.IntentBuilder
                    .from(requireActivity())
                    .setType(mimeType)
                    .setChooserTitle(resources.getString(R.string.share_the_film_now))
                    .setText(resources.getString(R.string.share_text, movie.title))
                    .startChooser()
            }
        }

        popularMoviesAdapter.favoriteCallback = { movie ->
            viewModel.updateFavorite(movie)
        }

        with(binding.rvPopularMovies) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = popularMoviesAdapter.withLoadStateHeaderAndFooter(
                header = popularLoadStateAdapter,
                footer = popularLoadStateAdapter
            )
        }
    }

    private fun setNowPlayingMoviesAdapter() {
        nowPlayingMoviesAdapter = MoviesAdapter()
        nowPlayingLoadStateAdapter = MoviesLoadStateAdapter(nowPlayingMoviesAdapter)

        nowPlayingMoviesAdapter.shareCallback = { movie ->
            if (activity != null) {
                val mimeType = "text/plain"
                ShareCompat.IntentBuilder
                    .from(requireActivity())
                    .setType(mimeType)
                    .setChooserTitle(resources.getString(R.string.share_the_film_now))
                    .setText(resources.getString(R.string.share_text, movie.title))
                    .startChooser()
            }
        }

        nowPlayingMoviesAdapter.favoriteCallback = { movie ->
            viewModel.updateFavorite(movie)
        }

        with(binding.rvNowPlayingMovies) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = nowPlayingMoviesAdapter.withLoadStateHeaderAndFooter(
                header = nowPlayingLoadStateAdapter,
                footer = nowPlayingLoadStateAdapter
            )
        }
    }

    private fun setupObserver() {
        setPopularMoviesObserver()
        setNowPlayingObserver()
        setLatestMovieObserver()
    }

    private fun setLatestMovieObserver() {
        viewModel.latestMovie.observe(viewLifecycleOwner) { movie ->
            showHighlightMovie(movie)
        }
    }

    private fun setPopularMoviesObserver() {
        lifecycleScope.launchWhenStarted {
            popularMoviesAdapter.loadStateFlow.collectLatest { loadState ->
                idlingResourceCheck(loadState)
                showLoadingAndHideFailureView(loadState.refresh is LoadState.Loading)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.popularMovies().collectLatest { pagingData ->
                popularMoviesAdapter.apply {
                    submitData(pagingData)
                    if (snapshot().size > 0) {
                        showHighlightMovie(snapshot()[0])
                    }
                }
            }
        }
    }

    private fun setNowPlayingObserver() {
        lifecycleScope.launchWhenResumed {
            nowPlayingMoviesAdapter.loadStateFlow.collectLatest { loadState ->
                idlingResourceCheck(loadState)
                showLoadingAndHideFailureView(loadState.refresh is LoadState.Loading)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.nowPlayingMovies().collectLatest { pagingData ->
                nowPlayingMoviesAdapter.submitData(pagingData)
            }
        }
    }

    private fun showHighlightMovie(movie: Movie?) {
        movie?.let {
            with(binding) {
                tvHightlightTitle.text = movie.title
                ivHighlight.setImage(requireContext(), movie.posterPath)
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

    private fun showLoadingAndHideFailureView(status: Boolean) {
        binding.lavFailure.gone()
        with(binding.lavLoading) {
            if (status) {
                playAnimation()
                visible()
            } else {
                gone()
            }
        }
    }

    private fun showError(status: Boolean) {
        if (status) {
            binding.lavFailure.visible()
        } else {
            binding.lavFailure.gone()
        }
    }
}
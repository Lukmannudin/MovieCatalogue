package com.lukmannudin.moviecatalogue.ui.tvshows

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
import com.lukmannudin.moviecatalogue.databinding.FragmentTvShowBinding
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
class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TvShowsViewModel by viewModels()
    private lateinit var tvShowsPopularAdapter: TvShowsAdapter
    private lateinit var tvShowsPopularloadStateAdapter: TvShowsLoadStateAdapter

    private lateinit var tvShowsOnAirAdapter: TvShowsAdapter
    private lateinit var tvShowsOnAirLoadStateAdapter: TvShowsLoadStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObserver()

        viewModel.getLatestTvShow()
    }

    private fun setupAdapter() {
        setTvShowPopularAdapter()
        setTvShowOnAirAdapter()
    }

    private fun setTvShowPopularAdapter() {
        tvShowsPopularAdapter = TvShowsAdapter()
        tvShowsPopularloadStateAdapter = TvShowsLoadStateAdapter(tvShowsPopularAdapter)

        tvShowsPopularAdapter.shareCallback = { movie ->
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

        tvShowsPopularAdapter.favoriteCallback = { movie ->
            viewModel.updateFavorite(movie)
        }

        with(binding.rvTvshows) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = tvShowsPopularAdapter.withLoadStateHeaderAndFooter(
                header = tvShowsPopularloadStateAdapter,
                footer = tvShowsPopularloadStateAdapter
            )
        }
    }

    private fun setTvShowOnAirAdapter() {
        tvShowsOnAirAdapter = TvShowsAdapter()
        tvShowsOnAirLoadStateAdapter = TvShowsLoadStateAdapter(tvShowsOnAirAdapter)

        tvShowsOnAirAdapter.shareCallback = { movie ->
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

        tvShowsOnAirAdapter.favoriteCallback = { movie ->
            viewModel.updateFavorite(movie)
        }

        with(binding.rvNowPlayingTvshows) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = tvShowsOnAirAdapter.withLoadStateHeaderAndFooter(
                header = tvShowsOnAirLoadStateAdapter,
                footer = tvShowsOnAirLoadStateAdapter
            )
        }
    }

    private fun setupObserver() {
        setTvShowsPopularObserver()
        setTvShowsOnAirObserver()
        setLatestTvShowObserver()
    }

    private fun setTvShowsPopularObserver() {
        lifecycleScope.launchWhenResumed {
            tvShowsPopularAdapter.loadStateFlow.collectLatest { loadState ->
                idlingResourceCheck(loadState)
                showLoadingAndHideFailureView(loadState.mediator?.refresh is LoadState.Loading)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.popularTvShows().collectLatest {
                tvShowsPopularAdapter.submitData(it)
            }
        }
    }

    private fun setTvShowsOnAirObserver() {
        lifecycleScope.launchWhenResumed {
            tvShowsOnAirAdapter.loadStateFlow.collectLatest { loadState ->
                idlingResourceCheck(loadState)
                showLoadingAndHideFailureView(loadState.mediator?.refresh is LoadState.Loading)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.onAirTvShows().collectLatest {
                tvShowsOnAirAdapter.submitData(it)
            }
        }
    }

    private fun setLatestTvShowObserver() {
        viewModel.latestTvShow.observe(viewLifecycleOwner) { tvShow ->
            binding.ivHighlight.setImage(requireContext(), tvShow.posterPath)
        }
    }

    // for ui testing
    private fun idlingResourceCheck(loadState: CombinedLoadStates){
        if (loadState.refresh == LoadState.Loading){
            EspressoIdlingResource.increment()
        } else {
            EspressoIdlingResource.decrement()
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

    private fun showError(status: Boolean) {
        if (status) {
            binding.lavFailure.visible()
        } else {
            binding.lavFailure.gone()
        }
    }
}
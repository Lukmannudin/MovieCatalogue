package com.lukmannudin.moviecatalogue.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.databinding.FragmentTvShowBinding
import com.lukmannudin.moviecatalogue.ui.tvshows.TvShowsViewModel.TvShowsState
import com.lukmannudin.moviecatalogue.utils.gone
import com.lukmannudin.moviecatalogue.utils.visible
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Lukmannudin on 5/3/21.
 */

@AndroidEntryPoint
class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TvShowsViewModel by viewModels()
    private lateinit var tvShowsAdapter: TvShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTvShows()
        setupAdapter()
        setupObserver()
    }

    private fun setupAdapter() {
        tvShowsAdapter = TvShowsAdapter()

        tvShowsAdapter.shareCallback = { tvShow ->
            if (activity != null) {
                val mimeType = "text/plain"
                ShareCompat.IntentBuilder
                    .from(requireActivity())
                    .setType(mimeType)
                    .setChooserTitle(resources.getString(R.string.share_the_film_now))
                    .setText(resources.getString(R.string.share_text, tvShow.title))
                    .startChooser()
            }
        }

        with(binding.rvTvshows) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowsAdapter
        }
    }

    private fun setupObserver(){
        viewModel.tvShowsState.observe(viewLifecycleOwner, { viewState ->
            when (viewState){
                is TvShowsState.Loading -> {
                    showLoadingAndHideFailureView(true)
                }
                is TvShowsState.Error -> {
                    binding.lavFailure.visible()
                }
                is TvShowsState.Loaded -> {
                    showLoadingAndHideFailureView(false)
                    tvShowsAdapter.setTvShows(viewState.tvShows)
                }
            }
        })
    }

    private fun showLoadingAndHideFailureView(status: Boolean){
        binding.lavFailure.gone()
        with(binding.lavLoading){
            if (status){
                visible()
            } else {
                gone()
            }
        }
    }
}
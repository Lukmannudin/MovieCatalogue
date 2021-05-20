package com.lukmannudin.moviecatalogue.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.databinding.FragmentMovieBinding
import com.lukmannudin.moviecatalogue.ui.movies.MoviesViewModel.MoviesState
import com.lukmannudin.moviecatalogue.utils.gone
import com.lukmannudin.moviecatalogue.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Lukmannudin on 5/3/21.
 */

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var loadStateAdapter: LoaderStateAdapter

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
        viewModel.getMovies()
        setupAdapter()
        setupObserver()
    }

    private fun setupAdapter() {
        moviesAdapter = MoviesAdapter()
        loadStateAdapter = LoaderStateAdapter()

        moviesAdapter.shareCallback = { movie ->
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

        loadStateAdapter.loadStateCallBack = { loadState ->
            when (loadState){
                is LoadState.Error -> {
                    viewModel.moviesState.postValue(
                        MoviesState.Error(loadState.error.message.toString())
                    )
                }
                is LoadState.Loading -> {
                    viewModel.moviesState.postValue(MoviesState.Loading)
                }
                is LoadState.NotLoading -> {
                    // do nothing
                }
            }
        }

        with(binding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter.withLoadStateHeaderAndFooter(
                loadStateAdapter, loadStateAdapter)
        }
    }

    private fun setupObserver(){
        viewModel.moviesState.observe(viewLifecycleOwner, { viewState ->
            when (viewState){
                is MoviesState.Loading -> {
                    showLoadingAndHideFailureView(true)
                }
                is MoviesState.Error -> {
                    binding.lavFailure.visible()
                }
                is MoviesState.Loaded -> {
                    showLoadingAndHideFailureView(false)
                    lifecycleScope.launch {
                        moviesAdapter.submitData(viewState.movies)
                    }
                }
            }
        })
    }

    private fun showLoadingAndHideFailureView(status: Boolean){
        binding.lavFailure.gone()
        with(binding.lavLoading){
            if (status){
                playAnimation()
                visible()
            } else {
                gone()
            }
        }
    }
}
package com.lukmannudin.moviecatalogue.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.databinding.FragmentMovieBinding
import com.lukmannudin.moviecatalogue.ui.movies.MoviesViewModel.MoviesState
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Lukmannudin on 5/3/21.
 */

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: MoviesAdapter

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

        with(binding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }
    }

    private fun setupObserver(){
        viewModel.moviesState.observe(viewLifecycleOwner, { viewState ->
            when (viewState){
                is MoviesState.Loading -> {}
                is MoviesState.Error -> {
                    Toast.makeText(requireActivity(), viewState.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is MoviesState.Loaded -> {
                    moviesAdapter.setMovies(viewState.movies)
                }
            }
        })
    }

}
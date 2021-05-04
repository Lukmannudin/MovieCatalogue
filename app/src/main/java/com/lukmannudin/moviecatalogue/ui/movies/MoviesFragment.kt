package com.lukmannudin.moviecatalogue.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.databinding.FragmentMovieBinding

/**
 * Created by Lukmannudin on 5/3/21.
 */

class MoviesFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

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

        if (activity != null) {
            val movies = viewModel.getMovies()
            moviesAdapter.setMovies(movies)
        }
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

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MoviesViewModel::class.java]
    }
}
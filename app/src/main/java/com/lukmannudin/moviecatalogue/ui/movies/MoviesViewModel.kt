package com.lukmannudin.moviecatalogue.ui.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.PagingData
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.moviessource.MovieRepository
import com.lukmannudin.moviecatalogue.utils.Constant.DEFAULT_LANGUAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Lukmannudin on 5/3/21.
 */

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val pagedListConfig: PagedList.Config,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val moviesState = MutableLiveData<MoviesState>()

//    fun getMovies() {
//        moviesState.value = MoviesState.Loading
//        viewModelScope.launch(ioDispatcher) {
//           when (val movies = movieRepository.getPopularMovies(DEFAULT_LANGUAGE, 1)){
//                is Result.Error -> {
//                    moviesState.postValue(MoviesState.Error(movies.exception.message.toString()))
//                }
//
//                is Result.Success -> {
//                    moviesState.postValue(MoviesState.Loaded(
//                        LivePagedListBuilder(movies.data, pagedListConfig).build()
//                    ))
//                }
//
//                else -> {
//                    moviesState.postValue(MoviesState.Error("Something Wrong"))
//                }
//            }
//        }
//    }

    fun getMovies() {
        viewModelScope.launch {
            try {
                movieRepository.getPopularMovies(DEFAULT_LANGUAGE, 100).collectLatest {
                    moviesState.postValue(
                        MoviesState.Loaded(it)
                    )
                }
            } catch (e: Exception) {
                moviesState.postValue(
                    MoviesState.Error(e.message.toString())
                )
            }
        }
    }

    sealed class MoviesState {
        object Loading : MoviesState()
        data class Error(val errorMessage: String) : MoviesState()
        data class Loaded(val movies: PagingData<Movie>) : MoviesState()
    }

}
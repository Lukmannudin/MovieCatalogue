package com.lukmannudin.moviecatalogue.data.tvshowssource

import com.lukmannudin.moviecatalogue.MainCoroutineRule
import com.lukmannudin.moviecatalogue.data.Movie
import com.lukmannudin.moviecatalogue.data.Result
import com.lukmannudin.moviecatalogue.data.TvShow
import com.lukmannudin.moviecatalogue.utils.Constant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

/**
 * Created by Lukmannudin on 11/05/21.
 */


@ExperimentalCoroutinesApi
class TvShowRepositoryImplTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var tvShowDataSource: TvShowDataSource

    @Mock
    private lateinit var tvShowRepository: TvShowRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        tvShowDataSource = FakeTvShowDataSource()
        tvShowRepository = TvShowRepositoryImpl(
            tvShowDataSource, testDispatcher
        )
    }

    @Test
    fun getValidPopularTvShows() = runBlockingTest {
        val tvShows = tvShowRepository.getPopularTvShows(Constant.DEFAULT_LANGUAGE, Constant.DEFAULT_PAGE_INDEX)
        assertEquals(Result.Success(emptyList<TvShow>()), tvShows)
    }

    @Test
    fun getValidTvShow() = runBlockingTest {
        val tvShow = tvShowRepository.getTvShow(1, Constant.DEFAULT_LANGUAGE)
        assertEquals(Result.Success(FakeTvShowDataSource.dummyTvShow), tvShow)
    }

    @Test
    fun getInvalidPopularTvShows() = runBlockingTest {
        val tvShowInvalidLanguage = tvShowRepository.getPopularTvShows("", Constant.DEFAULT_PAGE_INDEX)
        Assert.assertNotEquals(Result.Success(emptyList<TvShow>()), tvShowInvalidLanguage)

        val tvShowsInvalidPage = tvShowRepository.getPopularTvShows(Constant.DEFAULT_LANGUAGE, -1)
        Assert.assertNotEquals(Result.Success(emptyList<TvShow>()), tvShowsInvalidPage)

        val tvShowsInvalid = tvShowRepository.getPopularTvShows("", -1)
        Assert.assertNotEquals(Result.Success(emptyList<Movie>()), tvShowsInvalid)
    }

    @Test
    fun getInvalidTvShow() = runBlockingTest {
        val tvShow = FakeTvShowDataSource.dummyTvShow

        val tvShowInvalidId = tvShowRepository.getTvShow(-1, Constant.DEFAULT_LANGUAGE)
        Assert.assertNotEquals(Result.Success(tvShow), tvShowInvalidId)

        val tvShowInvalidLanguage = tvShowRepository.getTvShow(1, "")
        Assert.assertNotEquals(Result.Success(tvShow), tvShowInvalidLanguage)

        val tvShowInvalid = tvShowRepository.getTvShow(-1, "")
        Assert.assertNotEquals(Result.Success(tvShow), tvShowInvalid)
    }
}
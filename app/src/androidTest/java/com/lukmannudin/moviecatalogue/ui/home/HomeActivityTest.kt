package com.lukmannudin.moviecatalogue.ui.home


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.ui.Utils
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test

/**
 * Created by Lukmannudin on 5/4/21.
 */


class HomeActivityTest {
    private val dummyTvShows = TvShowsDummy.generateDummyTvShows()
    private val dummyMovies = MoviesDummy.generateDummyMovies()

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun loadMovies(){
        onView(withId(R.id.tabs)).perform(Utils.selectTabAtPosition(0))
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovies.size
            )
        )
    }

    @Test
    fun loadTvShows(){
        onView(withId(R.id.tabs)).perform(Utils.selectTabAtPosition(1))
        onView(withId(R.id.rv_tvshows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyTvShows.size
            )
        )
    }

    @Test
    fun loadDetailMovie(){
        onView(withId(R.id.tabs)).perform(Utils.selectTabAtPosition(0))
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )
        onView(allOf(withId(R.id.tv_title), withText(dummyMovies[0].title)))
    }

    @Test
    fun loadDetailTvShow(){
        onView(withId(R.id.tabs)).perform(Utils.selectTabAtPosition(1))
        onView(withId(R.id.rv_tvshows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )
        onView(allOf(withId(R.id.tv_title), withText(dummyTvShows[0].title)))
    }
}
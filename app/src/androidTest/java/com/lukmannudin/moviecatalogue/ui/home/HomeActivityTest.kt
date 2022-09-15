package com.lukmannudin.moviecatalogue.ui.home


import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.ui.selectTabAtPosition
import com.lukmannudin.moviecatalogue.utils.EspressoIdlingResource
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Lukmannudin on 5/4/21.
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    private lateinit var instrumentalContext: Context

    private val targetPosition = 10

    @Before
    fun setup() {
        instrumentalContext = InstrumentationRegistry.getInstrumentation().targetContext
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(0))
        onView(withId(R.id.rv_popular_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                targetPosition
            )
        )
    }

    @Test
    fun loadTvShows() {
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        onView(withId(R.id.rv_tvshows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                targetPosition
            )
        )
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(0))
        onView(withId(R.id.rv_popular_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )
        onView(allOf(withId(R.id.tv_title), not(withText(""))))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        onView(withId(R.id.rv_tvshows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )
        onView(allOf(withId(R.id.tv_title), not(withText(""))))
    }

    @Test
    fun loadFavoriteMovies() {
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(0))
        onView(withId(R.id.rv_popular_movies)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_popular_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
            (0, ClickOnBtnFavoriteMovie()))

        onView(withId(R.id.rv_popular_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
            (1, ClickOnBtnFavoriteMovie()))

        openActionBarOverflowOrOptionsMenu(instrumentalContext)
        onView(withText(instrumentalContext.getString(R.string.favorite_movies))).perform(click())
        onView(withId(R.id.rv_favorite_movies)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavoriteTvShow() {
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        onView(withId(R.id.rv_tvshows)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
            (1, ClickOnBtnFavoriteTvShow()))

        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
            (2, ClickOnBtnFavoriteTvShow()))

        openActionBarOverflowOrOptionsMenu(instrumentalContext)
        onView(withText(instrumentalContext.getString(R.string.favorite_tv_shows))).perform(click())
        onView(withId(R.id.rv_favorite_movies)).check(matches(isDisplayed()))
    }


    fun nthChildOf(parentMatcher: Matcher<View?>, childPosition: Int): Matcher<View?>? {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with $childPosition child view of type parentMatcher")
            }

            override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) {
                    return parentMatcher.matches(view.parent)
                }
                val group = view.parent as ViewGroup
                return parentMatcher.matches(view.parent) && group.getChildAt(childPosition) == view
            }
        }
    }

    inner class ClickOnBtnFavoriteMovie : ViewAction {
        private var click = click()

        override fun getConstraints(): Matcher<View> {
            return click.constraints
        }

        override fun getDescription(): String {
            return " click on custom button view"
        }

        override fun perform(uiController: UiController, view: View) {
            click.perform(uiController, view.findViewById(R.id.cb_favorite))
        }
    }

    inner class ClickOnBtnFavoriteTvShow : ViewAction {
        private var click = click()

        override fun getConstraints(): Matcher<View> {
            return click.constraints
        }

        override fun getDescription(): String {
            return " click on custom button view"
        }

        override fun perform(uiController: UiController, view: View) {
            click.perform(uiController, view.findViewById(R.id.cb_favorite))
        }
    }

}
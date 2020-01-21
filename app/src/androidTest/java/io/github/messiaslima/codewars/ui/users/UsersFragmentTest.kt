package io.github.messiaslima.codewars.ui.users

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.messiaslima.codewars.R
import io.github.messiaslima.codewars.ui.common.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsersFragmentTest {

    lateinit var mainActivity: ActivityScenario<MainActivity>

    @Before
    fun init() {
        mainActivity = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun load_saved_users() {
        onView(withId(R.id.usersRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun search_user() {
        onView(withId(R.id.menu_item_users_search)).perform(click())
        onView(withId(R.id.searchDialogButton)).check(matches(isDisplayed()))
        onView(withId(R.id.userSearchEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.userSearchEditText)).perform(typeText("messiaslima"))
        onView(withId(R.id.searchDialogButton)).perform(click())
        onView(withId(R.id.loagingLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun select_saved_user() {
        onView(withId(R.id.usersRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<UsersAdapter.UsersViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.userDetailLayout)).check(matches(isDisplayed()))
    }
}
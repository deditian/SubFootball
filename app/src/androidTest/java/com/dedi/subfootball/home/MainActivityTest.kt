package com.dedi.subfootball.home

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.dedi.subfootball.MainActivity
import com.dedi.subfootball.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        delay()
        Espresso.onView(ViewMatchers.withId(rvLast))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        delay()
        Espresso.onView(ViewMatchers.withId(rvLast)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))

        Espresso.onView(ViewMatchers.withId(rvLast)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, ViewActions.click()))
        delay()

        Espresso.onView(ViewMatchers.withId(awayImg)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(homeImg)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(favorite)).perform(ViewActions.click())

        Espresso.pressBack()
        delay()

        Espresso.onView(ViewMatchers.withId(pager)).perform(ViewActions.swipeLeft())

        //ke next save favorite

        delay()
        Espresso.onView(ViewMatchers.withId(rvNext))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        delay()
        Espresso.onView(ViewMatchers.withId(rvNext)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))

        Espresso.onView(ViewMatchers.withId(rvNext)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, ViewActions.click()))
        delay()

        Espresso.onView(ViewMatchers.withId(awayImg)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(homeImg)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(favorite)).perform(ViewActions.click())

        Espresso.pressBack()
        delay()

        Espresso.onView(ViewMatchers.withId(favMatch)).perform(ViewActions.click())

        delay()
    }






    private fun delay(){
        try {
            Thread.sleep(4000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
package com.dinesh.kotlinstructure.ui.activity

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.dinesh.kotlinstructure.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LauncherActivityTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<LauncherActivity> = ActivityTestRule(LauncherActivity::class.java, false, true)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {

    }

    @Test
    fun launch_activity() {
        Thread.sleep(8000)
        Espresso.onView(ViewMatchers.withId(R.id.rvContent)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        Thread.sleep(5000)

    }
}
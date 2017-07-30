package com.bacecek.yamblz;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bacecek.yamblz.ui.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by vandrikeev on 30.07.17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void openAbout() {
        onView(withContentDescription(R.string.drawer_open)).perform(click());

        onView(withText(R.string.action_about_app))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(R.string.app_name))
                .check(matches(isDisplayed()));
    }

    @Test
    public void openSettings() {
        onView(withContentDescription(R.string.drawer_open)).perform(click());

        onView(withText(R.string.action_settings))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.settings_temp_units))
                .check(matches(isDisplayed()));
        onView(withId(R.id.settings_interval_update))
                .check(matches(isDisplayed()));
        onView(withId(R.id.settings_city))
                .check(matches(isDisplayed()));
    }
}

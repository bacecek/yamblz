package com.bacecek.yamblz;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bacecek.yamblz.ui.activity.SettingsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by vandrikeev on 30.07.17.
 */
@RunWith(AndroidJUnit4.class)
public class SettingsActivityTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivity = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void setInterval() {

        onView(withId(R.id.settings_interval_update))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText("Off"))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.txt_settings_period), withText("Off")))
                .check(matches(isDisplayed()));
    }
}

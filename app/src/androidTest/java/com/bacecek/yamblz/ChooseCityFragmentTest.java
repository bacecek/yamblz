package com.bacecek.yamblz;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bacecek.yamblz.ui.activity.SettingsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.bacecek.yamblz.ActionUtils.sleep;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by vandrikeev on 30.07.17.
 */
@RunWith(AndroidJUnit4.class)
public class ChooseCityFragmentTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivity = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void chooseCity() {

        onView(withId(R.id.settings_city))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.txt_input_query))
                .check(matches(isDisplayed()))
                .perform(typeText("Moscow"));

        onView(isRoot())
                .perform(sleep(TimeUnit.SECONDS.toMillis(2)));

        closeSoftKeyboard();

        onView(withId(R.id.rv_suggestions))
                .perform(actionOnItemAtPosition(0, click()));

        onView(allOf(withId(R.id.txt_city),
                withText("Moscow"))).check(matches(isDisplayed()));
    }
}

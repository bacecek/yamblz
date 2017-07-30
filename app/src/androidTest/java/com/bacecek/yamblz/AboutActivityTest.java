package com.bacecek.yamblz;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bacecek.yamblz.ui.activity.AboutActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasCategories;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.intent.matcher.UriMatchers.hasHost;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.EasyMock2Matchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;

/**
 * Created by vandrikeev on 30.07.17.
 */
@RunWith(AndroidJUnit4.class)
public class AboutActivityTest {

    @Rule
    public ActivityTestRule<AboutActivity> aboutActivity = new ActivityTestRule<>(AboutActivity.class);

    @Test
    public void openSourceCode() {
        onView(withText(R.string.about_source_code_title))
                .check(matches(isDisplayed()))
                .perform(click());

        intended(allOf(
                hasAction(equalTo(Intent.ACTION_VIEW)),
                hasCategories(hasItem(equalTo(Intent.CATEGORY_BROWSABLE))),
                hasData(hasHost(equalTo("https://github.com/bacecek/yamblz"))),
                toPackage("com.android.browser")));
    }

    @Test
    public void sendEmail() {
        onView(withText(R.string.about_contact_me))
                .check(matches(isDisplayed()))
                .perform(click());

        intended(hasAction(equalTo(Intent.ACTION_SENDTO)));
    }
}

package com.bacecek.yamblz;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.Locale;

import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * Created by vandrikeev on 30.07.17.
 */
class ActionUtils {

    static ViewAction sleep(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return String.format(Locale.ENGLISH, "Sleep on main thread for %d millis", millis);
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }
}

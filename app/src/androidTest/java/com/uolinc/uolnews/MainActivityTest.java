package com.uolinc.uolnews;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.uolinc.uolnews.global.UolApplication;
import com.uolinc.uolnews.ui.MainActivity;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Test
    public void test_this() throws IOException {
        init();

        activityRule.launchActivity(new Intent());
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        SystemClock.sleep(1000);
    }

    private void init() throws IOException {
        MockWebServer server = new MockWebServer();
        String response = Util.readFileFromAssets(InstrumentationRegistry.getContext(), "response.json", 0);
        server.enqueue(new MockResponse().setBody(response));
        server.start();
        UolApplication.apiUrl = server.url("/test/").toString();
    }
}

package br.com.uol.uolnews.tests;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import br.com.uol.uolnews.R;
import br.com.uol.uolnews.global.UolApplication;
import br.com.uol.uolnews.ui.MainActivity;
import br.com.uol.uolnews.util.RecyclerViewMatcher;
import br.com.uol.uolnews.util.Util;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
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
    private static final String TAG = "MainActivityTest";

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Test
    public void test_this() throws IOException {
        init();

        activityRule.launchActivity(new Intent());
        onView(ViewMatchers.withText(R.string.app_name)).check(matches(isDisplayed()));
        onView(new RecyclerViewMatcher(R.id.rv_list).atPosition(0)).check(matches(hasDescendant(withText("Jogador de futebol larga carreira e vira spec na UOL"))));
        onView(new RecyclerViewMatcher(R.id.rv_list).atPosition(0)).check(matches(hasDescendant(withText("13/07, às 03h12"))));
        onView(new RecyclerViewMatcher(R.id.rv_list).atPosition(0)).perform(click());
    }

    private void init() throws IOException {
        MockWebServer server = new MockWebServer();
        String response = Util.readFileFromAssets(InstrumentationRegistry.getContext(), "response.json", 0);
        server.enqueue(new MockResponse().setBody(response));
        Log.d(TAG, response);
        System.out.println(response);
        server.start();
        UolApplication.apiUrl = server.url("/test/").toString();
    }
}

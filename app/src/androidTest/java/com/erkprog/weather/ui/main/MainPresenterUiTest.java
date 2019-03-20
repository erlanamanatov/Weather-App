package com.erkprog.weather.ui.main;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.erkprog.weather.R;
import com.erkprog.weather.data.entity.City;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class)
public class MainPresenterUiTest {
  private static final String LONDON = "London";


  //launch activity before any tests
  @Rule
  public ActivityTestRule<MainActivity> mMainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

  @Test
  public void tt() throws Exception {
    //search city and check result in spinner
    onView(withId(R.id.img_search_city)).perform(click());
    onView(isAssignableFrom(EditText.class)).perform(typeText(LONDON), pressImeActionButton());
    onView(withText(containsString(LONDON))).check(matches(isDisplayed()));

    IdlingResource idlingResource = new ElapsedTimeIdlingResource(800);
    Espresso.registerIdlingResources(idlingResource);
    onData(instanceOf(City.class)).atPosition(0).perform(click());
    onView(withId(R.id.main_city_spinner)).check(matches(withSpinnerText(containsString(LONDON))));
    Espresso.unregisterIdlingResources(idlingResource);
  }

  public class ElapsedTimeIdlingResource implements IdlingResource {
    private final long startTime;
    private final long waitingTime;
    private ResourceCallback resourceCallback;

    public ElapsedTimeIdlingResource(long waitingTime) {
      this.startTime = System.currentTimeMillis();
      this.waitingTime = waitingTime;
    }

    @Override
    public String getName() {
      return ElapsedTimeIdlingResource.class.getName() + ":" + waitingTime;
    }

    @Override
    public boolean isIdleNow() {
      long elapsed = System.currentTimeMillis() - startTime;
      boolean idle = (elapsed >= waitingTime);
      if (idle) {
        resourceCallback.onTransitionToIdle();
      }
      return idle;
    }

    @Override
    public void registerIdleTransitionCallback(
        ResourceCallback resourceCallback) {
      this.resourceCallback = resourceCallback;
    }
  }

}
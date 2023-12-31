/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SmartAquaSwitchTest {

    private final static String OFF = "OFF";
    private final static String ON = "ON";

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testSwitchFragmentSwitchDisplayed() {
        SmartAquaSwitch switchFragment = new SmartAquaSwitch();

        activityRule.getScenario().onActivity(activity -> activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.SmartAquaDrawerLayout, switchFragment)
                .commit());

        onView(withId(R.id.SmartAquaSwitchAir)).check(matches(isDisplayed()));
    }


    @Test
    public void clickAirSwitchPress() {
        SmartAquaSwitch switchFragment = new SmartAquaSwitch();

        activityRule.getScenario().onActivity(activity -> activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.SmartAquaDrawerLayout, switchFragment)
                .commit());

        onView(withId(R.id.SmartAquaSwitchAir)).perform(click());
        onView(withId(R.id.SmartAquaSwitchStatusAir)).check(matches(withText(ON)));
    }

    @Test
    public void clickBubbleSwitchPress() {
        SmartAquaSwitch switchFragment = new SmartAquaSwitch();

        activityRule.getScenario().onActivity(activity -> activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.SmartAquaDrawerLayout, switchFragment)
                .commit());

        onView(withId(R.id.SmartAquaSwitchBubble)).perform(click());
        onView(withId(R.id.SmartAquaSwitchStatusBubble)).check(matches(withText(ON)));
    }

    @Test
    public void clickSwitchFragmentDoesNotExist() {
        SmartAquaSwitch switchFragment = new SmartAquaSwitch();

        activityRule.getScenario().onActivity(activity -> activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.SmartAquaDrawerLayout, switchFragment)
                .commit());

        onView(withId(R.id.SmartAquaFeedbackBtn)).check(doesNotExist());
    }

    @Test
    public void checkSwitchAirTextView() {
        SmartAquaSwitch switchFragment = new SmartAquaSwitch();

        activityRule.getScenario().onActivity(activity -> activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.SmartAquaDrawerLayout, switchFragment)
                .commit());

        onView(withId(R.id.SmartAquaSwitchStatusAir)).check(matches(withText(OFF)));
    }

    @Test
    public void checkSwitchBubbleTextView() {
        SmartAquaSwitch switchFragment = new SmartAquaSwitch();

        activityRule.getScenario().onActivity(activity -> activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.SmartAquaDrawerLayout, switchFragment)
                .commit());

        onView(withId(R.id.SmartAquaSwitchStatusBubble)).check(matches(withText(OFF)));
    }
}
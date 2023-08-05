/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */
package ca.buckleupinc.it.smartaqua;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SmartAquaSwitchTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void clickButtonSwitch(){
        //SmartAquaSwitch test = new SmartAquaSwitch();
        //View view = test.getView();

        SmartAquaSwitch switchFragment = new SmartAquaSwitch();

        activityRule.getScenario().onActivity(activity -> activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.SmartAquaDrawerLayout, switchFragment)
                .commit());

        Espresso.onView(withId(R.id.SmartAquaSwitchAir)).perform(click());
    }

    @Test
    public void testSwitchFragment(){
        SmartAquaSwitch switchFragment = new SmartAquaSwitch();

        activityRule.getScenario().onActivity(activity -> activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.SmartAquaDrawerLayout, switchFragment)
                .commit());

    }
}
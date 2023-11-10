/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.os.Build;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)

public class SmartAquaTemperatureTest {

    private FragmentActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(FragmentActivity.class).create().get();
    }

    // Invalid Test Case 1
    //@Test
    //public void testSeekBarProgressAndTemperatureRange_invalid_negativeProgress() {
        //SmartAquaTemperature fragment = new SmartAquaTemperature();
        //FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        //transaction.add(fragment, null).commit();

        //SeekBar seekBar = fragment.getView().findViewById(R.id.SmartAquaTempSeekBar);

        //seekBar.setProgress(-10);

        //TextView textView = fragment.getView().findViewById(R.id.SmartAquaTempReading3);
        //assertEquals("18°C", textView.getText().toString());
        //}

    // Invalid Test Case 2
    //@Test(expected = IllegalArgumentException.class)
    //public void testInvalidSeekBarProgress() {
    //SmartAquaTemperature fragment = new SmartAquaTemperature();
    //FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
    //transaction.add(fragment, null).commit();

        //SeekBar seekBar = fragment.getView().findViewById(R.id.SmartAquaTempSeekBar);

        //seekBar.setProgress(30);
    //}

    // Invalid Test Case 3
    @Test
    public void testTextViewInitialization_invalid() {
        SmartAquaTemperature fragment = new SmartAquaTemperature();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(fragment, null).commit();

        TextView textView = fragment.getView().findViewById(R.id.SmartAquaTempReading3);
        assertNotNull(textView);
    }

    // Invalid Test Case 4
    // @Test
    //public void testInvalidTemperatureDisplay() {
    //SmartAquaTemperature fragment = new SmartAquaTemperature();
    //FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
    //transaction.add(fragment, null).commit();

        //SeekBar seekBar = fragment.getView().findViewById(R.id.SmartAquaTempSeekBar);

        //seekBar.setProgress(9);

    //TextView textView = fragment.getView().findViewById(R.id.SmartAquaTempReading3);
    // assertNotEquals("18°C", textView.getText().toString());
    // }

    // Valid Test Case 1
    @Test
    public void testFragmentNotNull() {
        SmartAquaTemperature fragment = new SmartAquaTemperature();
        assertNotNull("Fragment should not be null", fragment);
    }
}

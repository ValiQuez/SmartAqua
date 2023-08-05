package ca.buckleupinc.it.smartaqua;

import android.os.Build;
import android.widget.SeekBar;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class SmartAquaTemperatureTest {

    private FragmentActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(FragmentActivity.class).create().start().resume().get();
    }

    // Invalid Test Case 1
    @Test(expected = NullPointerException.class)
    public void testSeekBarProgressAndTemperatureRange_invalid_nullView() {
        SmartAquaTemperature fragment = new SmartAquaTemperature();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(fragment, null).commit();

        SeekBar seekBar = fragment.getView().findViewById(R.id.SmartAquaTempSeekBar);
        // Attempting to access the view of a fragment before its onCreateView() is called
        // This should throw a NullPointerException
    }

    // Invalid Test Case 2
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSeekBarProgress() {
        SmartAquaTemperature fragment = new SmartAquaTemperature();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(fragment, null).commit();

        SeekBar seekBar = fragment.getView().findViewById(R.id.SmartAquaTempSeekBar);

        // Attempting to set progress beyond the maximum value (100)
        seekBar.setProgress(120);
        // This should throw an IllegalArgumentException as it's an invalid progress value
    }
}

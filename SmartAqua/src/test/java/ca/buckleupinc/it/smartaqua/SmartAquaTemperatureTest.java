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
        activity = Robolectric.buildActivity(FragmentActivity.class).create().start().resume().get();
    }

    // Invalid Test Case 1
    @Test
    public void testSeekBarProgressAndTemperatureRange_invalid_negativeProgress() {
        SmartAquaTemperature fragment = new SmartAquaTemperature();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(fragment, null).commit();

        SeekBar seekBar = fragment.getView().findViewById(R.id.SmartAquaTempSeekBar);

        // Attempt to set a negative progress, which is invalid
        seekBar.setProgress(-10);

        // Negative progress should not update the temperature text
        TextView textView = fragment.getView().findViewById(R.id.SmartAquaTempReading3);
        assertEquals("18°C", textView.getText().toString());
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

    // Invalid Test Case 3
    @Test
    public void testTextViewInitialization_invalid() {
        SmartAquaTemperature fragment = new SmartAquaTemperature();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(fragment, null).commit();

        TextView textView = fragment.getView().findViewById(R.id.SmartAquaTempReading3);
        assertNotNull(textView); // This will fail because the textView is not initialized yet
    }
}

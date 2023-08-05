/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import android.os.Build;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}) // Specify the SDK version
public class SmartAquaTemperatureTest {

    private FragmentActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(FragmentActivity.class).create().get();
    }

    // Valid Test Case 1
    @Test
    public void testSeekBarProgressAndTemperatureRange() {
        activity.getSupportFragmentManager().beginTransaction()
                .add(new SmartAquaTemperature(), null)
                .commitNow();

        Fragment scenario = activity.getSupportFragmentManager().findFragmentById(R.id.SmartAquaTempLayout);

        SeekBar seekBar = scenario.getView().findViewById(R.id.SmartAquaTempSeekBar);
        TextView textView = scenario.getView().findViewById(R.id.SmartAquaTempReading3);

        // Set seekBar progress to 50
        seekBar.setProgress(50);

        // Calculate the expected temperature range based on the formula used in the fragment
        int expectedTemperatureRange = (int) (50 * 0.09) + 18;

        // Construct the expected temperature text
        String expectedTemperatureText = expectedTemperatureRange + scenario.getString(R.string.tempCelcius);

        // Verify if the displayed temperature text matches the expected value
        assertEquals(expectedTemperatureText, textView.getText().toString());
    }

}

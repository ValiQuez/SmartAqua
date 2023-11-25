/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.os.Build;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class SmartAquaTemperatureTest {

    private SmartAquaTemperature smartAquaTemperature;

    @Before
    public void setUp() {
        smartAquaTemperature = new SmartAquaTemperature();
    }

    @Test
    public void testSmartAquaTemperatureNotNull() {
        assertNotNull("SmartAquaTemperature instance should not be null", smartAquaTemperature);
    }

    @Test
    public void testInitialization() {
        assertEquals(0, smartAquaTemperature.getTemperature());
    }

    @Test
    public void testTemperatureSetting() {

        smartAquaTemperature.setTemperature(25);
        assertEquals(25, smartAquaTemperature.getTemperature());
    }

    @Test
    public void testWaterLevel() {
        smartAquaTemperature.setWaterLevel(50);
        assertEquals(50, smartAquaTemperature.getWaterLevel());
    }

    @Test
    public void testTemperatureAboveThreshold() {

        smartAquaTemperature.setTemperature(105);
        assertTrue(smartAquaTemperature.isTemperatureTooHigh());
    }

}

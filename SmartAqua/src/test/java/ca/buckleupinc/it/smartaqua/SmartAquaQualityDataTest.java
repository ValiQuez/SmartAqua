/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmartAquaQualityDataTest {

    @Test
    public void validTest_GetReading_TDS() {
        String reading = "234";
        String status = "Danger!";
        SmartAquaQualityData test1 = new SmartAquaQualityData(reading, status);
        assertEquals("234", test1.getReading_TDS());
    }

    @Test
    public void validTest_getStatus_TDS() {
        String reading = "407";
        String status = "Good!";
        SmartAquaQualityData test2 = new SmartAquaQualityData(reading, status);
        assertEquals("Good!", test2.getStatus_TDS());
    }

    @Test
    public void invalidTest_GetReading_TDS() {
        String reading = "234";
        String status = "Danger!";
        SmartAquaQualityData test3 = new SmartAquaQualityData(reading, status);
        assertNotEquals("450", test3.getReading_TDS());
    }

    @Test
    public void invalidTest_getStatus_TDS() {
        String reading = "407";
        String status = "Good!";
        SmartAquaQualityData test4 = new SmartAquaQualityData(reading, status);
        assertNotEquals("Danger!", test4.getReading_TDS());
    }

    @Test
    public void overwriteReading() {
        String reading = "407";
        String status = "Good!";
        SmartAquaQualityData test5 = new SmartAquaQualityData(reading, status);
        test5.setReading_TDS("598");
        assertEquals("598", test5.getReading_TDS());
    }

}
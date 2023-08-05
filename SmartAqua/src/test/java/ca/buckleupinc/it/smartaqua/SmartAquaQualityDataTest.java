package ca.buckleupinc.it.smartaqua;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmartAquaQualityDataTest {

    @Test
    public void validTest_GetReading_TDS() {
        String reading = "234";
        String status = "Danger!";
        SmartAquaQualityData test1 = new SmartAquaQualityData(reading,status);
        assertEquals("234",test1.getReading_TDS());
    }
    @Test
    public void validTest_getStatus_TDS() {
        String reading = "407";
        String status = "Good!";
        SmartAquaQualityData test1 = new SmartAquaQualityData(reading,status);
        assertEquals("Good!",test1.getStatus_TDS());
    }

    @Test
    public void invalidTest_GetReading_TDS() {
        String reading = "234";
        String status = "Danger!";
        SmartAquaQualityData test1 = new SmartAquaQualityData(reading,status);
        assertNotEquals("450",test1.getReading_TDS());
    }
    @Test
    public void invalidTest_getStatus_TDS() {
        String reading = "407";
        String status = "Good!";
        SmartAquaQualityData test1 = new SmartAquaQualityData(reading,status);
        assertNotEquals("Danger!",test1.getStatus_TDS());
    }

}
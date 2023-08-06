package ca.buckleupinc.it.smartaqua;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmartAquaReviewsTest {

    private final String name = "Rodrigo";
    private final String number = "5199021759";
    private final String email = "rchavez@gmail.com";
    private final String comment = "Nice app!";
    private final String model = "LG Nexus 5";
    private final float rating = 4;

    @Test
    public void identifyWrongField(){
        SmartAquaReviews commentTest = new SmartAquaReviews(name, email, comment, model, number, rating);
        assertEquals("Sebastian",commentTest.getComment());
    }

     @Test
    public void overwriteName() {
        SmartAquaReviews nameTest = new SmartAquaReviews(name, email, comment, model, number, rating);
        nameTest.setName("Alvaro");
        assertEquals("Alvaro",nameTest.getName());
    }

    @Test
    public void setOverRatingBar() {
        SmartAquaReviews rateBar = new SmartAquaReviews(name, email, comment, model, number, rating);
        rateBar.setRating(9);
        assertNotEquals(5,rateBar.getRating());
    }

    @Test
    public void identifyRating() {
        SmartAquaReviews rateBar = new SmartAquaReviews(name, email, comment, model, number, rating);
        rateBar.setRating(4);
        assertEquals(4,rateBar.getRating(),0.0);
    }

    @Test
    public void identifyPhoneModel(){
        SmartAquaReviews modelPhone = new SmartAquaReviews(name, email, comment, model, number, rating);
        assertEquals("LG Nexus 5",modelPhone.getModel());
    }

}
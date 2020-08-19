package edu.tacoma.uw.jasonli7.team12project;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.tacoma.uw.jasonli7.team12project.model.Device;
import edu.tacoma.uw.jasonli7.team12project.model.DeviceContent;
import edu.tacoma.uw.jasonli7.team12project.model.Review;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DeviceUnitTest {

    @Test
    public void testDeviceConstructor() {
        List<Review> reviews =  new ArrayList<>();
        Arrays.asList("A", "B", 5);
        assertNotNull(new Device("pixel" ,reviews,2000));
    }
    @Test
    public void testgetDeviceNameEquals(){
        List<Review> reviews =  new ArrayList<>();
        Arrays.asList("A", "B", 5);
        new Device("pixel" ,reviews,2000);
        assertEquals("failure - strings are not equal", "pixel", "pixel");

    }
    @Test
    public void testgetReviewsEquals(){
        List<Review> reviews =  new ArrayList<>();
        Arrays.asList("A", "B", 5);
        new Device("pixel" ,reviews,2000);
        assertEquals("failure - strings are not equal", "\"A\", \"B\", 5", "\"A\", \"B\", 5");
    }

    @Test
    public void testgetPriceEquals(){
        List<Review> reviews =  new ArrayList<>();
        Arrays.asList("A", "B", 5);
        new Device("pixel" ,reviews,2000);
        assertEquals("failure - strings are not equal", "2000", "2000");
    }

    @Test
    public void testcompareToEquals(){
        List<Review> reviews =  new ArrayList<>();
        Arrays.asList("A", "B", 5);
        assertEquals(0,new Device("pixel" ,reviews,2000).compareTo(new Device("iphone" ,reviews,2000)));

    }
}
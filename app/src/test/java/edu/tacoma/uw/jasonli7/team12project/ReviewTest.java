package edu.tacoma.uw.jasonli7.team12project;

import org.junit.Test;

import edu.tacoma.uw.jasonli7.team12project.model.Review;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ReviewTest {
    @Test
    public void testReviewConstructor() {
        assertNotNull(new Review("pixel","good", 5));
    }
}
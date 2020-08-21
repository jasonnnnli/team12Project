package edu.tacoma.uw.jasonli7.team12project.model;

/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 3rd Aug 2020.
 *
 * A class that stores a Review.
 */
public class InfoHolder {
    public static class InfoPass{

        private static Review mReview;
        public InfoPass() {
        }
        public static Review getmReview() {
            return mReview;
        }
        public static void setmReview(Review mReview) {
            InfoPass.mReview = mReview;
        }


    }

}

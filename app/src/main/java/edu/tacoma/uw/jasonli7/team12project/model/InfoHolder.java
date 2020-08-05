package edu.tacoma.uw.jasonli7.team12project.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 3rd Aug 2020.
 *
 * A class to coordinate the dummy data until back end is implemented.
 */
public class InfoHolder {
    public static class InfoPass{
        public static String getmEmail() {
            return mEmail;
        }

        private static String mEmail;
        private static List<Review> mReviewList;

        private static Review mReview;
        public InfoPass() {
            mEmail = "";
            mReviewList = new ArrayList<>();
        }
        public static List<Review> getReviewList() {
            return mReviewList;
        }

        public static void setReviewList(List<Review> reviewList) {
            mReviewList = reviewList;
        }

        public String getEmail() {
            return mEmail;
        }

        public static void setEmail(String email) {
            mEmail = email;
        }
        public static Review getmReview() {
            return mReview;
        }

        public static void setmReview(Review mReview) {
            InfoPass.mReview = mReview;
        }

    }

}

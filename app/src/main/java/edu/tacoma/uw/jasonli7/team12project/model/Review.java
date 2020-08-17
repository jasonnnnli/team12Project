package edu.tacoma.uw.jasonli7.team12project.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 2nd Aug 2020.
 *
 * A class for creating review objects.
 */
public class Review {
    public static final String USER_NAME = "username";
    public static final String DEVICE_NAME = "devicename";
    public static final String REVIEW_CONTENT = "reviewcontent";
    public static final String RATING = "rating";

    private String mUserName;
    private String mDevName;
    private String mReview;
    private double mRate;

    /**
     * Constructor.
     *
     * @param name
     * @param devName
     * @param review
     * @param rating
     */
    public Review(String name, String devName, String review, double rating) {
        mUserName = name;
        mDevName = devName;
        mReview = review;
        mRate = rating;
    }
    public String getmUserName() {
        return mUserName;
    }
    public String getmDeviceName() {
        return mDevName;
    }
    public double getRate() {
        return mRate;
    }
    public String getmReview() {
        return mReview;
    }

    /**
     * Parses review objects from json objects.
     *
     * @param reviewJson
     * @throws JSONException
     */
    public static void parseReviewJson(String reviewJson) throws JSONException {

        if (reviewJson != null) {
            DeviceContent.mReviews = new ArrayList<>();

            JSONArray arr = new JSONArray(reviewJson);

            for (int i = 0; i < arr.length(); i++) {

                JSONObject obj = arr.getJSONObject(i);
                Review review = new Review(obj.getString(Review.USER_NAME),obj.getString(Review.DEVICE_NAME),
                        obj.getString(Review.REVIEW_CONTENT),Double.parseDouble(obj.getString(Review.RATING)));
                DeviceContent.mReviews.add(review);
            }
        }
    }
}

package edu.tacoma.uw.jasonli7.team12project.model;

import android.media.Rating;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 2nd Aug 2020.
 *
 * A class for creating Device objects..
 */
public class Device implements Comparable<Device> {

    private String mDeviceName;
    private int mNumberOfReviews;
    private double mAvgRate;
    private List<Review> mReviews;
    private double mPrice;
public static final String DEVICE_NAME = "devicename";
public static final String DEVICE_LIST = "devicelist";


    public  Device(String name, List<Review> reviews, double price) {
        mDeviceName = name;
        mNumberOfReviews = reviews.size();
        mReviews = reviews;
        mAvgRate = calcAvg(mReviews);
        mPrice = price;
    }



    public int getmNumberOfReviews() {
        return mNumberOfReviews;
    }
    private double calcAvg(List<Review> reviews) {
    double toReturn = 0.0;
    for (Review r : reviews) {
        toReturn+=r.getRate();
    }
    return toReturn/mNumberOfReviews;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public void setDeviceName(String mDeviceName) {
        this.mDeviceName = mDeviceName;
    }
    public List<Review> getReviews() {
        return mReviews;
    }

    public void addNewReview(Review review) {
        mReviews.add(review);
    }

    public double getAvgRate() {
        return mAvgRate;
    }

    public double getPrice() {
        return mPrice;
    }


   /* public static List<Device> parseDeviceJson(String deviceJson) throws JSONException {
        List<Device> deviceList = new ArrayList<>();
        if (deviceJson != null) {

            JSONArray arr = new JSONArray(deviceJson);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String s = obj.getString("devicename"); //name of device.
                JSONArray jl = obj.getJSONArray("reviewlist"); //corresponding array.
                List<Review> revs = new ArrayList<>(); //holds review objects.
                Review forList;                        //Review container to load list.
                for (int j = 0; j < jl.length(); j++) {
                    JSONObject o = jl.getJSONObject(j);  //object from the inner array.
                    String name = o.getString("reviewer"); //get reviewers name.
                    String review = o.getString("review"); //get the corresponding review.
                    double rate = Double.parseDouble(o.getString("rate")); //parse rating.
                    forList = new Review(name, review, rate); //load the rating object.
                    revs.add(forList);                       //add it the list of Ratings.
                }
                Device device = new Device(s, revs);  //load individual list and device name into device object.

                deviceList.add(device);
            }
        }
        return deviceList;
    }*/
   public static List<Device> parseDeviceJson(String deviceJson) throws JSONException {
       List<Device> devices = new ArrayList<>();

       if (deviceJson != null) {
           DeviceContent.loadReviews();
           List<Review> dev = DeviceContent.mReviews;

           JSONArray arr = new JSONArray(deviceJson);

           for (int i = 0; i < arr.length(); i++) {
               DeviceContent.loadReviews();
               dev = DeviceContent.mReviews;
               JSONObject obj = arr.getJSONObject(i);
               Device device = new Device(obj.getString(Device.DEVICE_NAME), dev, DeviceContent.mockPrice());
               DeviceContent.addItem(device);
               devices.add(device);
           }
       }
       return devices;
   }

    @Override
    public int compareTo(Device device) {
       if (DeviceContent.SORT_PRICE) {
           return (int) ((int) this.getPrice() - device.getPrice());
       } else {
           return (int) ((int) (this.getAvgRate() *100) - (device.getAvgRate()* 100));
       }
    }
}

package edu.tacoma.uw.jasonli7.team12project.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 19th Aug 2020.
 *
 * A class to hold and process features objects.
 */
public class Features {
    public static HashMap<String, ArrayList<String>> FEATURE_MAP = new HashMap<>();

    private String mDevice;
    private String mFeature;
    public Features(String device, String feature){
       mDevice = device;
       mFeature = feature;
    }


    public String getDevice(){
        return mDevice;
    }
    public String getFeature(){
        return mFeature;
    }

    public static void addFeature(String device, String feature) {
        FEATURE_MAP.get(device).add(feature);
    }
    public static ArrayList<String> getFeatures(String device) {
        return FEATURE_MAP.get(device);
    }
    public static boolean exists(String device) {
        return FEATURE_MAP.containsKey(device);
    }
    public static void addToDevice(String device, String feature) {
        if (exists(device)) {
            FEATURE_MAP.get(device).add(feature);
        } else {
            addDevice(device);
            FEATURE_MAP.get(device).add(feature);

        }
    }
    public static void addDevice(String device) {
        FEATURE_MAP.put(device, new ArrayList<String>());
    }

    /**
     * Parses features from JSON objects.
     *
     * @param feature
     * @throws JSONException
     */
    public static void parseFeatureJson(String feature)   throws JSONException {
        FEATURE_MAP = new HashMap<>();
        if (feature != null) {


            JSONArray arr = new JSONArray(feature);

            for (int i = 0; i < arr.length(); i++) {

                JSONObject obj = arr.getJSONObject(i);
               addToDevice(obj.getString("devicename"), obj.getString("featurecontent"));
            }
        }
    }
}

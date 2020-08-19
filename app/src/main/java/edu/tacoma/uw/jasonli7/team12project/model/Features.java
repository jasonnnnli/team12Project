package edu.tacoma.uw.jasonli7.team12project.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Features {
    static HashMap<String, ArrayList<String>> FEATURE_MAP = new HashMap<>();

    public static void addFeature(String device, String feature) {
        FEATURE_MAP.get(device).add(feature);
    }
    public static ArrayList<String> getFeatures(String device) {
        return FEATURE_MAP.get(device);
    }
    public static boolean exists(String device) {
        return FEATURE_MAP.containsKey(device);
    }
    public static void addDevice(String device) {
        FEATURE_MAP.put(device, new ArrayList<String>());
    }
    public static void loadMap() {
        String s = "Bat Phone";
        addDevice(s);
        addFeature(s, "Batarang");
        addFeature(s, "Grappling Hook");
        addFeature(s, "Gps tracking device");

    }
}

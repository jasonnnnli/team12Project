package edu.tacoma.uw.jasonli7.team12project.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 2nd Aug 2020.
 *
 * A class for creating dummy data.
 */
public class DeviceContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Device> ITEMS = new ArrayList<Device>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static  Map<String, Device> ITEM_MAP = new HashMap<String, Device>();

    public static double PRICE_MIN = 0;

    public static double PRICE_MAX = 1000000;

    public static  List<Review> mReviews;

    public static int POSITION = 0;

    public static  boolean SORT_PRICE = false;

    private static final int COUNT = 25;







    private static Device createDeviceItem(int position) {

       // loadReviews();
        return new Device("Device: " + String.valueOf(position), mReviews, 100.56);

    }
    static void addItem(Device item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getDeviceName(), item);
    }



    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Device: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


    public static double mockPrice() {
        Random q = new Random();
        return q.nextDouble() * 1000;
    }
    public static void priceSort(boolean sortPrice) {
       Device[] device = new Device[ITEMS.size()];
       int i = 0;
       for (Device d: ITEMS) {
           device[i] = d;
           i++;
       }
       SORT_PRICE = sortPrice;
       Arrays.sort(device);
       ITEMS = new ArrayList<Device>();
       ITEM_MAP = new HashMap<String, Device>();
       for (Device dev: device) {
           addItem(dev);
       }

       }


}
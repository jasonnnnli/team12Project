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

    /**
     * Adds items to the map and list.
     *
     * @param item
     */
    static void addItem(Device item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getDeviceName(), item);
    }

    /**
     * Generates random amounts to send to back end as "price".
     *
     * @return
     */
    public static double mockPrice() {
        Random q = new Random();
        return q.nextDouble() * 1000;
    }

    /**
     * sorts price and rating.
     *
     * @param sortPrice
     */
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
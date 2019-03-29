package com.sktl.nbrbcurrency;

import android.net.Uri;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Util {
    private static final String TAG = "sssU";

    private static final String DOMAIN = "http://www.nbrb.by";
    private static final String CURRENCY = "/API/ExRates/Rates?";


    private static final String ONDATE = "onDate="; //?onDate=2016-7-6&Periodicity=0
    private static final String PERIODICITY = "Periodicity=0"; //сегодня


    public static URL generateURL(String date) {
        Uri uri = Uri.parse(DOMAIN + CURRENCY + ONDATE + date + "&" + PERIODICITY).buildUpon().build();
        URL uRL = null;
        try {
            uRL = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
        }
        return uRL;
    }


    public static URL generateURL() {
        Uri uri = Uri.parse(DOMAIN + CURRENCY + PERIODICITY).buildUpon().build();
        URL uRL = null;
        try {
            uRL = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
        }
        return uRL;
    }

    public static String getResponseFromURL(URL uRL) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) uRL.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanr = new Scanner(inputStream);
            scanr.useDelimiter("\\A");

            boolean hasInput = scanr.hasNext();

            if (hasInput) {
                return scanr.next();
            } else {
                return "{\"status\":\"hasInput==false\"}";
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
            return "{\"status\":\"e.getResponseFromURL\"}";
        } finally {
            urlConnection.disconnect();
        }


    }


}

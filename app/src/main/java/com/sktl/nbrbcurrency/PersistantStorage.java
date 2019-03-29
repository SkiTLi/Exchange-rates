package com.sktl.nbrbcurrency;

import android.content.Context;
import android.content.SharedPreferences;

public class PersistantStorage {
    public static final String STORAGE_NAME = "SKTLstorage";


    private static boolean visited = false;

    private static SharedPreferences settings = null;

    private static SharedPreferences.Editor editor = null;
    private static Context context = null;

    public static void init(Context cntxt) {
        context = cntxt;
    }


    private static void init() {
        settings = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public static boolean isVisited() {
        if (settings == null) {
            init();
        }

        return visited;
    }

    public static boolean hasProperty(String name) {
        if (settings == null) {
            init();
        }
        if (settings.contains(name)) {
            return true;
        } else {
            return false;
        }
    }


    public static void setVisited(boolean visited) {
        if (settings == null) {
            init();
        }
        PersistantStorage.visited = visited;
    }


    public static void addProperty(String name, int value) {
        if (settings == null) {
            init();
        }
        editor.putInt(name, value);
        editor.apply();
    }

    public static int getProperty(String name) {
        if (settings == null) {
            init();
        }
        return settings.getInt(name, 7777);
    }


}

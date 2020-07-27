package com.sagesurfer.nami.storage.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;




/*
 * This file contains Shared preferences for application
 * Used to save basic user information and counter json
 */

public class Preferences {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void initialize(Context con) {
        if (null == preferences) {
            preferences = PreferenceManager.getDefaultSharedPreferences(con);
        }
        if (null == editor) {
            editor = preferences.edit();
            editor.apply();
        }
    }

    public static void clear() {
        editor.clear();
        editor.commit();
    }

    public static void save(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static void save(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void save(String key, Integer value) {
        save(key, String.valueOf(value));
    }


    public static void save(String key, Long value) {
        save(key, String.valueOf(value));
    }

    public static String get(String key) {
        return preferences.getString(key, "");
    }


    public static boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public static Boolean contains(String key) {
        return preferences.contains(key);
    }


    public static void removeKey(String key) {
        editor.remove(key);
        editor.commit();
    }
}

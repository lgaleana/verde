package com.oracle.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lgaleana on 5/1/15.
 */
public class Data {
    public static final int APP_NONE = 0;
    public static final int APP_READY = 1;
    public static final int APP_SET = 2;

    public static final int RIDE_WAITING = 0;
    public static final int RIDE_ACCEPTED = 1;
    public static final int RIDE_ON = 2;

    private static final String PREFS = "preferences";
    private static final String APP_STATE = "appState";
    private static final String RIDE_STATE = "rideState";

    Context context;

    public Data(Context context) {
        this.context = context;
    }

    public int getAppState() {
        SharedPreferences settings = context.getSharedPreferences(PREFS, 0);
        return settings.getInt(APP_STATE, 0);
    }

    public void setAppState(int state) {
        SharedPreferences settings = context.getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(APP_STATE, state);
        editor.commit();
    }

    public int getRideState() {
        SharedPreferences settings = context.getSharedPreferences(PREFS, 0);
        return settings.getInt(RIDE_STATE, 0);
    }

    public void setRideState(int state) {
        SharedPreferences settings = context.getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(RIDE_STATE, state);
        editor.commit();
    }
}

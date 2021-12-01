package com.fidget.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by leej431 on 7/4/17.
 */

public class Settings {

    public final static String file = ".phidget";

    public static final String SWIPE_ENABLE = "SWIPE_ENABLE";
    public static final String VIBRATION_ENABLED = "VIBRATION_ENABLED";
    public static final String FIDGET_MODEL = "FIDGET_MODEL";
    public static final String POINTS = "POINTS";

    public static boolean swipeEnabled;
    public static boolean vibrationEnabled;
    public static int fidgetModel;
    public static int points;

    public static Preferences prefs;

    public static void save() {
        if (prefs == null) {
            prefs = Gdx.app.getPreferences(file);
        }
        new Thread(new Runnable() {
            public void run() {
                System.out.println("save() called taco");
                try {
                    prefs.putBoolean(SWIPE_ENABLE, swipeEnabled);
                    prefs.putBoolean(VIBRATION_ENABLED, vibrationEnabled);
                    prefs.putInteger(FIDGET_MODEL, fidgetModel);
                    prefs.putInteger(POINTS, points);

                    StoreSettings.save();

                    prefs.flush();
                } catch (Throwable e) {
                }
            }
        }).start();
    }

    public static void load() {
        if (prefs == null) {
            prefs = Gdx.app.getPreferences(file);
        }

        swipeEnabled = prefs.getBoolean(SWIPE_ENABLE, true);
        vibrationEnabled = prefs.getBoolean(VIBRATION_ENABLED, true);
        fidgetModel = prefs.getInteger(FIDGET_MODEL, 0);
        points = prefs.getInteger(POINTS, 0);
        StoreSettings.load();
    }
}

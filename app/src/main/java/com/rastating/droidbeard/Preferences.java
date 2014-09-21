package com.rastating.droidbeard;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    public final static String API_KEY = "api_key";
    public final static String SICKBEARD_URL = "sickbeard_url";
    public final static String ACKNOWLEDGED_EPISODE_HELP = "acknowledged_episode_help";
    public final static String ACKNOWLEDGED_SHOW_ADDING_HELP = "acknowledged_show_adding_help";

    private Context mContext;

    public Preferences(Context context) {
        mContext = context;
    }

    public String getApiKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String key = preferences.getString(API_KEY, null);
        return key != null ? key.trim() : null;
    }

    public String getSickbeardUrl() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String url = preferences.getString(SICKBEARD_URL, null);

        if (url != null && !url.toLowerCase().startsWith("http://")) {
            url = "http://" + url;
        }

        if (url != null && !url.endsWith("/")) {
            url += "/";
        }

        return url != null ? url.trim() : null;
    }

    public boolean hasAcknowledgedEpisodeHelp() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getBoolean(ACKNOWLEDGED_EPISODE_HELP, false);
    }

    public boolean hasAcknowledgedShowAddingHelp() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getBoolean(ACKNOWLEDGED_SHOW_ADDING_HELP, false);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
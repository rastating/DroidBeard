package com.rastating.droidbeard;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    public final static String API_KEY = "api_key";
    public final static String SICKBEARD_URL = "sickbeard_url";

    private Context mContext;

    public Preferences(Context context) {
        mContext = context;
    }

    public String getApiKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString(API_KEY, null);
    }

    public String getSickbeardUrl() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String url = preferences.getString(SICKBEARD_URL, null);
        if (url != null && !url.endsWith("/")) {
            url += "/";
        }

        return url;
    }
}
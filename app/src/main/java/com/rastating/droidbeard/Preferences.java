package com.rastating.droidbeard;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Preferences {
    public final static String API_KEY = "api_key";
    public final static String PORT_NUMBER = "port";
    public final static String USE_HTTPS = "use_https";
    public final static String TRUST_ALL_CERTIFICATES = "trust_all_certificates";
    public final static String EXTENSION_PATH = "extension_path";
    public final static String ADDRESS = "address";
    public final static String V1_SICKBEARD_URL = "sickbeard_url";
    public final static String ACKNOWLEDGED_EPISODE_HELP = "acknowledged_episode_help";
    public final static String ACKNOWLEDGED_SHOW_ADDING_HELP = "acknowledged_show_adding_help";

    private Context mContext;

    public Preferences(Context context) {
        mContext = context;

        // Ensure any users from version 1.0 have their preferences updated.
        String url = getV1Url();
        if (url != null && url.trim().length() > 0) {
            Pattern pattern = Pattern.compile("(http(s?)://)?([^:/]+)(:[0-9]+)?(/.*)?");
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                String prefix = matcher.group(1);
                String address = matcher.group(3);
                String port = matcher.group(4);
                String path = matcher.group(5);
                boolean useHTTPS = prefix != null && prefix.contains("https");

                if (port != null) {
                    port = port.replace(":", "");
                }

                putBoolean(Preferences.USE_HTTPS, useHTTPS);
                putString(Preferences.ADDRESS, address);
                putString(Preferences.PORT_NUMBER, port);
                putString(Preferences.EXTENSION_PATH, path);
                putBoolean(Preferences.TRUST_ALL_CERTIFICATES, true);
            }

            putString(Preferences.V1_SICKBEARD_URL, null);
        }
    }

    public String getApiKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String key = preferences.getString(API_KEY, null);
        return key != null ? key.trim() : null;
    }

    public String getSickbeardUrl() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String address = preferences.getString(Preferences.ADDRESS, null);
        String port = preferences.getString(Preferences.PORT_NUMBER, null);
        String path = preferences.getString(Preferences.EXTENSION_PATH, "/");
        boolean useHTTPS = preferences.getBoolean(Preferences.USE_HTTPS, false);
        boolean trustAllCertificates = preferences.getBoolean(Preferences.TRUST_ALL_CERTIFICATES, true);

        String url = useHTTPS ? "https://" : "http://";
        url += address;

        if (port != null) {
            url += ":" + port;
        }

        url += path;

        return url.trim();
    }

    public boolean getTrustAllCertificatesFlag() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getBoolean(Preferences.TRUST_ALL_CERTIFICATES, true);
    }

    public String getV1Url() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String url = preferences.getString(V1_SICKBEARD_URL, null);

        if (url != null && !url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("https://")) {
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

    public void putInt(String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putString(String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
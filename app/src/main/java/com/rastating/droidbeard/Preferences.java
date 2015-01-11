/*
     DroidBeard - a free, open-source Android app for managing SickBeard
     Copyright (C) 2014 Robert Carr

     This program is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     This program is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with this program.  If not, see http://www.gnu.org/licenses/.
*/

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
    public final static String HTTP_USERNAME = "http_username";
    public final static String HTTP_PASSWORD = "http_password";
    public final static String GROUP_INACTIVE_SHOWS = "group_inactive_shows";

    private Context mContext;

    public Preferences(Context context) {
        mContext = context;

        // Ensure any users from version 1.0 have their preferences updated.
        String url = getV1Url();
        if (url != null && url.trim().length() > 0) {
            Pattern pattern = Pattern.compile("(http(s?)://)?([^:/]+)(:[0-9]+)?(/.*)?", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                String prefix = matcher.group(1);
                String address = matcher.group(3);
                String port = matcher.group(4);
                String path = matcher.group(5);
                boolean useHTTPS = prefix != null && prefix.toLowerCase().contains("https");

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

        // Set trust all certificates to true if migrating to v1.2
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (!preferences.contains(Preferences.TRUST_ALL_CERTIFICATES)) {
            putBoolean(Preferences.TRUST_ALL_CERTIFICATES, true);
        }

        // Set status grouping to true if migrating to v1.3
        if (!preferences.contains(Preferences.GROUP_INACTIVE_SHOWS)) {
            putBoolean(Preferences.GROUP_INACTIVE_SHOWS, true);
        }
    }

    public String getAddress() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String address = preferences.getString(ADDRESS, "");
        return address;
    }

    public String getApiKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String key = preferences.getString(API_KEY, null);
        return key != null ? key.trim() : null;
    }

    public boolean getGroupInactiveShows() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getBoolean(Preferences.GROUP_INACTIVE_SHOWS, true);
    }

    public String getHttpUsername() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String username = preferences.getString(HTTP_USERNAME, "");
        return username;
    }

    public String getHttpPassword() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String password = preferences.getString(HTTP_PASSWORD, "");
        return password;
    }

    public int getPort() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String port = preferences.getString(PORT_NUMBER, "");
        return Integer.valueOf(port);
    }

    public boolean getHttpsEnabled() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getBoolean(Preferences.USE_HTTPS, false);
    }

    public String getSickbeardUrl() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String address = preferences.getString(Preferences.ADDRESS, "");
        String port = preferences.getString(Preferences.PORT_NUMBER, null);
        String path = preferences.getString(Preferences.EXTENSION_PATH, "/");
        boolean useHTTPS = preferences.getBoolean(Preferences.USE_HTTPS, false);
        boolean trustAllCertificates = preferences.getBoolean(Preferences.TRUST_ALL_CERTIFICATES, true);

        if (address == null || address.trim().equals("")) {
            return null;
        }
        else {
            String url = useHTTPS ? "https://" : "http://";
            url += address.trim();

            if (port != null) {
                url += ":" + port.trim();
            }

            url += path.trim().equals("") ? "/" : path;
            url = url.trim();

            if (!url.endsWith("/")) {
                url += "/";
            }

            return url;
        }
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
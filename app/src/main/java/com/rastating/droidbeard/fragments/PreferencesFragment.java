package com.rastating.droidbeard.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rastating.droidbeard.MainActivity;
import com.rastating.droidbeard.Preferences;
import com.rastating.droidbeard.R;
import com.rastating.droidbeard.net.HttpClientManager;

public class PreferencesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences preferences = getPreferenceScreen().getSharedPreferences();

        EditTextPreference preference = (EditTextPreference) findPreference("address");
        String value = preferences.getString("address", null);
        if (value != null && !value.equals("")) {
            preference.setSummary(value);
        }

        preference = (EditTextPreference) findPreference("port");
        value = preferences.getString("port", null);
        if (value != null && !value.equals("")) {
            preference.setSummary(value);
        }

        preference = (EditTextPreference) findPreference("extension_path");
        value = preferences.getString("extension_path", null);
        if (value != null && !value.equals("")) {
            preference.setSummary(value);
        }

        preference = (EditTextPreference) findPreference("api_key");
        value = preferences.getString("api_key", null);
        if (value != null && !value.equals("")) {
            preference.setSummary(value);
        }

        preference = (EditTextPreference) findPreference("http_username");
        value = preferences.getString("http_username", null);
        if (value != null && !value.equals("")) {
            preference.setSummary(value);
        }

        preference = (EditTextPreference) findPreference("http_password");
        value = preferences.getString("http_password", null);
        if (value != null && !value.equals("")) {
            int n = value.length();
            if (n > 0) {
                preference.setSummary(String.format(String.format("%%0%dd", n), 0).replace("0", "*"));
            }
            else {
                preference.setSummary("");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_preferences, container, false);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference instanceof EditTextPreference) {
            EditTextPreference editTextPreference = (EditTextPreference) preference;
            if (key.equals(Preferences.HTTP_PASSWORD)) {
                int n = editTextPreference.getText().length();
                if (n > 0) {
                    preference.setSummary(String.format(String.format("%%0%dd", n), 0).replace("0", "*"));
                }
                else {
                    preference.setSummary("");
                }
            }
            else {
                preference.setSummary(editTextPreference.getText());
            }
        }

        HttpClientManager.INSTANCE.invalidateClient();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.setCurrentFragment(this);
        }
    }
}

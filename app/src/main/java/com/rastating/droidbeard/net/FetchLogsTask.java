package com.rastating.droidbeard.net;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchLogsTask extends SickbeardAsyncTask<Void, Void, String[]> {
    public FetchLogsTask(Context context) {
        super(context);
    }

    @Override
    protected String[] doInBackground(Void... voids) {
        String json = getJson("logs", "min_level", "info");
        List<String> logs = new ArrayList<String>();

        try {
            JSONArray data = new JSONObject(json).getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                logs.add(data.getString(i));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return logs.toArray(new String[logs.size()]);
    }
}

package com.rastating.droidbeard.net;

import android.content.Context;

import com.rastating.droidbeard.entities.HistoricalEvent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchHistoryTask extends SickbeardAsyncTask<Void, Void, HistoricalEvent[]> {

    public FetchHistoryTask(Context context) {
        super(context);
    }

    @Override
    protected HistoricalEvent[] doInBackground(Void... voids) {
        List<HistoricalEvent> events = new ArrayList<HistoricalEvent>();

        try {
            String json = getJson("history", null);
            JSONArray results = new JSONObject(json).getJSONArray("data");
            for (int i = 0; i < results.length(); i++) {
                JSONObject data = results.getJSONObject(i);
                HistoricalEvent event = new HistoricalEvent();
                event.setDate(data.getString("date"));
                event.setEpisodeNumber(data.getInt("episode"));
                event.setProvider(data.getString("provider"));
                event.setQuality(data.getString("quality"));
                event.setSeason(data.getInt("season"));
                event.setShowName(data.getString("show_name"));
                event.setStatus(data.getString("status"));
                events.add(event);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return events.toArray(new HistoricalEvent[events.size()]);
    }
}
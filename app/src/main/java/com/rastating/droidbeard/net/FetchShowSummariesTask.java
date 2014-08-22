package com.rastating.droidbeard.net;

import com.rastating.droidbeard.entities.TVShowSummary;
import com.rastating.droidbeard.TVShowSummaryComparator;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FetchShowSummariesTask extends SickbeardAsyncTask<Void, Void, TVShowSummary[]> {

    public FetchShowSummariesTask(Context context) {
        super(context);
    }

    @Override
    protected TVShowSummary[] doInBackground(Void... voids) {
        List<TVShowSummary> shows = new ArrayList<TVShowSummary>();
        String json = getJson("shows", null);

        try {
            JSONObject root = new JSONObject(json);
            JSONObject data = root.getJSONObject("data");
            Iterator<String> keys = data.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONObject show = data.getJSONObject(key);
                int tvdbid = show.getInt("tvdbid");

                TVShowSummary tvShowSummary = new TVShowSummary(show.getString("show_name"));
                tvShowSummary.setNetwork(show.getString("network"));
                tvShowSummary.setTvDbId(tvdbid);

                try {
                    String nextDateString = show.getString("next_ep_airdate");
                    if (!nextDateString.equals("")) {
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(nextDateString);
                        tvShowSummary.setNextAirDate(date);
                    }
                } catch (ParseException e) {
                    // TODO: Handle exception
                    e.printStackTrace();
                }

                shows.add(tvShowSummary);
            }
        }
        catch (JSONException ex) {
            // TODO: handle exception
            System.out.println(ex.getMessage());
        }

        Collections.sort(shows, new TVShowSummaryComparator());
        return shows.toArray(new TVShowSummary[shows.size()]);
    }

    @Override
    protected void onPostExecute(TVShowSummary[] shows) {
        List<ApiResponseListener<TVShowSummary[]>> listeners = getResponseListeners();
        for (ApiResponseListener<TVShowSummary[]> listener : listeners) {
            listener.onApiRequestFinished(shows);
        }
    }
}
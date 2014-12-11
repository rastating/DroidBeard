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

package com.rastating.droidbeard.net;

import com.rastating.droidbeard.entities.TVShowSummary;
import com.rastating.droidbeard.comparators.TVShowSummaryComparator;

import android.content.Context;

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

        try {
            String json = getJson("shows", null);
            JSONObject root = new JSONObject(json);
            JSONObject data = root.getJSONObject("data");
            Iterator<String> keys = data.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject show = data.getJSONObject(key);

                int tvdbid = Integer.valueOf(key);

                TVShowSummary tvShowSummary = new TVShowSummary(show.getString("show_name"));
                tvShowSummary.setNetwork(show.getString("network"));
                tvShowSummary.setTvDbId(tvdbid);
                tvShowSummary.setStatus(show.getString("status"));

                Object pausedState = show.get("paused");
                if (pausedState instanceof Boolean) {
                    tvShowSummary.setPaused((Boolean) pausedState);
                }
                else {
                    tvShowSummary.setPaused(pausedState.equals(1));
                }

                try {
                    String nextDateString = show.getString("next_ep_airdate");
                    if (!nextDateString.equals("")) {
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(nextDateString);
                        tvShowSummary.setNextAirDate(date);
                    }
                } catch (ParseException e) {
                    tvShowSummary.setNextAirDate(null);
                }

                shows.add(tvShowSummary);
            }
        }
        catch (Exception e) {
            setLastException(e);
            e.printStackTrace();
            return null;
        }

        Collections.sort(shows, new TVShowSummaryComparator());
        return shows.toArray(new TVShowSummary[shows.size()]);
    }
}
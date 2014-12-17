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

import android.content.Context;

import com.rastating.droidbeard.entities.TvDBResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchTvDBTask extends SickbeardAsyncTask<String, Void, TvDBResult[]> {
    public SearchTvDBTask(Context context) {
        super(context);
    }

    @Override
    protected TvDBResult[] doInBackground(String... strings) {
        String name = null;
        try {
            name = URLEncoder.encode(strings[0], "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            name = strings[0];
        }

        try {
            String json = getJson("sb.searchtvdb", "name", name);
            List<TvDBResult> list = new ArrayList<TvDBResult>();

            if (json != null && !json.equals("")) {
                JSONArray results = new JSONObject(json).getJSONObject("data").getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);
                    TvDBResult tvDBResult = new TvDBResult();
                    tvDBResult.setFirstAired(result.getString("first_aired"));
                    tvDBResult.setName(result.getString("name"));
                    tvDBResult.setId(result.getLong("tvdbid"));
                    list.add(tvDBResult);
                }

                return list.toArray(new TvDBResult[list.size()]);
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
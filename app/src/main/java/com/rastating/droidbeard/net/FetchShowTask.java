package com.rastating.droidbeard.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Pair;

import com.rastating.droidbeard.entities.TVShow;
import com.rastating.droidbeard.entities.Language;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FetchShowTask extends SickbeardAsyncTask<Integer, Void, TVShow> {

    public FetchShowTask(Context context) {
        super(context);
    }

    private Bitmap getBanner(int tvdbid) {
        ArrayList<Pair<String, Object>> params = new ArrayList<Pair<String, Object>>();
        params.add(new Pair<String, Object>("tvdbid", tvdbid));
        return getBitmap("show.getbanner", params);
    }

    private TVShow getTVShow(int tvdbid) {
        ArrayList<Pair<String, Object>> params = new ArrayList<Pair<String, Object>>();
        params.add(new Pair<String, Object>("tvdbid", tvdbid));
        String json = getJson("show", params);

        if (json != null && !json.equals("")) {
            try {
                JSONObject data = new JSONObject(json).getJSONObject("data");
                TVShow show = new TVShow();

                show.setAirByDate(data.getInt("air_by_date") == 1);
                show.setAirs(data.getString("airs"));
                show.setFlattenFolders(data.getInt("flatten_folders") == 1);

                JSONArray genresJsonArray = data.getJSONArray("genre");
                String[] genres = new String[genresJsonArray.length()];
                for (int i = 0; i < genresJsonArray.length(); i++) {
                    genres[i] = genresJsonArray.getString(i);
                }

                show.setGenres(genres);
                show.setLanguage(new Language(data.getString("language")));
                show.setLocation(data.getString("location"));
                show.setNetwork(data.getString("network"));

                try {
                    String nextDateString = data.getString("next_ep_airdate");
                    if (!nextDateString.equals("")) {
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(nextDateString);
                        show.setNextAirdate(date);
                    }
                } catch (ParseException e) {
                    show.setNextAirdate(null);
                }

                show.setPaused(data.getInt("paused") == 1);
                show.setQuality(data.getString("quality"));
                show.setShowName(data.getString("show_name"));
                show.setStatus(data.getString("status"));

                return show;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            return null;
        }
    }

    @Override
    protected TVShow doInBackground(Integer... integers) {
        int tvdbid = integers[0];
        TVShow show = getTVShow(tvdbid);

        if (show != null) {
            Bitmap banner = getBanner(tvdbid);
            show.setBanner(banner);
        }

        return show;
    }

    @Override
    protected void onPostExecute(TVShow show) {
        List<ApiResponseListener<TVShow>> listeners = getResponseListeners();
        for (ApiResponseListener<TVShow> listener : listeners) {
            listener.onApiRequestFinished(show);
        }
    }
}

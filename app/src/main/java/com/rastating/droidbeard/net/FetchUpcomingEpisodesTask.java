package com.rastating.droidbeard.net;

import android.content.Context;

import com.rastating.droidbeard.comparators.UpcomingEpisodeComparator;
import com.rastating.droidbeard.entities.UpcomingEpisode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FetchUpcomingEpisodesTask extends SickbeardAsyncTask<Void, Void, UpcomingEpisode[]> {
    public FetchUpcomingEpisodesTask(Context context) {
        super(context);
    }

    @Override
    protected UpcomingEpisode[] doInBackground(Void... voids) {
        List<UpcomingEpisode> episodes = new ArrayList<UpcomingEpisode>();

        try {
            String json = getJson("future", null);
            JSONObject data = new JSONObject(json).getJSONObject("data");
            JSONArray missed = data.getJSONArray("missed");
            JSONArray today = data.getJSONArray("today");
            JSONArray soon = data.getJSONArray("soon");
            JSONArray later = data.getJSONArray("later");

            processEpisodes(missed, UpcomingEpisode.UpcomingEpisodeStatus.PAST, episodes);
            processEpisodes(today, UpcomingEpisode.UpcomingEpisodeStatus.CURRENT, episodes);
            processEpisodes(soon, UpcomingEpisode.UpcomingEpisodeStatus.FUTURE, episodes);
            processEpisodes(later, UpcomingEpisode.UpcomingEpisodeStatus.DISTANT, episodes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Collections.sort(episodes, new UpcomingEpisodeComparator());
        return episodes.toArray(new UpcomingEpisode[episodes.size()]);
    }

    private void processEpisodes(JSONArray data, UpcomingEpisode.UpcomingEpisodeStatus status, List<UpcomingEpisode> episodes) throws JSONException {
        for (int i = 0; i < data.length(); i++) {
            JSONObject episodeData = data.getJSONObject(i);
            UpcomingEpisode episode = new UpcomingEpisode();
            episode.setAirdate(episodeData.getString("airdate"));
            episode.setName(episodeData.getString("ep_name"));
            episode.setEpisodeNumber(episodeData.getInt("episode"));
            episode.setSeasonNumber(episodeData.getInt("season"));
            episode.setShowName(episodeData.getString("show_name"));
            episode.setUpcomingStatus(status);
            episodes.add(episode);
        }
    }
}